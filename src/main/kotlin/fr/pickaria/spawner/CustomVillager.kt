package fr.pickaria.spawner

import net.minecraft.commands.arguments.EntityAnchorArgument
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.targeting.TargetingConditions
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.item.trading.MerchantOffers
import net.minecraft.world.level.levelgen.structure.BoundingBox
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftMerchant
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftMerchantRecipe
import kotlin.random.Random


class CustomVillager(location: Location, title: String) :
	Villager(EntityType.VILLAGER, (location.world as CraftWorld).handle) {

	companion object {
		private fun boundingBox(center: Vec3, x: Double, y: Double, z: Double): BoundingBox =
			BoundingBox(
				(center.x - x).toInt(),
				(center.y - y).toInt(),
				(center.z - z).toInt(),
				(center.x + x).toInt(),
				(center.y + y).toInt(),
				(center.z + z).toInt()
			)
	}

	private val target: Vec3
	private val maxDist: Int = 3
	private val shop: CraftMerchant = Shop(title, this)
	private var moveIn: Int = 0
	private val level = (location.world as CraftWorld).handle

	init {
		target = Vec3(location.blockX.toDouble(), location.blockY.toDouble(), location.blockZ.toDouble())

		this.setPos(location.x, location.y, location.z)
		this.brain.removeAllBehaviors()
	}

	public override fun updateTrades() {
		this.offers = MerchantOffers().apply {
			shop.recipes.forEach {
				add((it as CraftMerchantRecipe).toMinecraft())
			}
		}
	}

	override fun getCraftMerchant(): CraftMerchant = shop

	override fun tick() {
		super.tick()

		val boundingBox = boundingBox(position(), 3.0, 3.0, 3.0)
		val aabb = AABB.of(boundingBox)

		// Look at nearest player
		level.getNearestEntity(
			ServerPlayer::class.java,
			TargetingConditions.forNonCombat(),
			this,
			position().x,
			position().y,
			position().z,
			aabb
		)?.let {
			navigation.stop()
			val pos: Vec3 = it.getEyePosition(0F)
			lookAt(EntityAnchorArgument.Anchor.EYES, pos)
			true
		} ?: run {
			if (navigation.isDone && moveIn-- < 0) {
				val x = target.x + Random.Default.nextInt(-maxDist, maxDist)
				val y = target.y + Random.Default.nextInt(-maxDist, maxDist)
				val z = target.z + Random.Default.nextInt(-maxDist, maxDist)
				val target = BlockPos(x.toInt(), y.toInt(), z.toInt())
				val path = this.navigation.createPath(target, 0)
				this.navigation.moveTo(path, 0.5)
				moveIn = Random.nextInt(20, 100)
			}
		}
	}
}
