package fr.pickaria.warden

import net.minecraft.commands.arguments.EntityAnchorArgument
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.targeting.TargetingConditions
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.level.levelgen.structure.BoundingBox
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftMerchant
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
	private val maxDist: Int = 5

	init {
		target = Vec3(location.blockX.toDouble(), location.blockY.toDouble(), location.blockZ.toDouble())

		this.setPos(location.x, location.y, location.z)
		this.brain.removeAllBehaviors()
	}

	private val shop: CraftMerchant = Shop(title, this.bukkitEntity as org.bukkit.entity.Villager)
 	override fun getCraftMerchant(): CraftMerchant = shop

	override fun tick() {
		super.tick()

		if (navigation.isDone) {
			// Look at nearest player
			val boundingBox = boundingBox(position(), 5.0, 5.0, 5.0)
			val aabb = AABB.of(boundingBox)

			level.getNearestEntity(
				ServerPlayer::class.java,
				TargetingConditions.forNonCombat(),
				this,
				position().x,
				position().y,
				position().z,
				aabb
			)?.let {
				val pos: Vec3 = it.getEyePosition(0F)
				lookAt(EntityAnchorArgument.Anchor.EYES, pos)
				true
			} ?: run {
				val x = target.x + Random.Default.nextInt(-maxDist, maxDist)
				val y = target.y + Random.Default.nextInt(-maxDist, maxDist)
				val z = target.z + Random.Default.nextInt(-maxDist, maxDist)
				val target = BlockPos(x, y, z)
				val path = this.navigation.createPath(target, 0)
				this.navigation.moveTo(path, 0.5)
			}
		}
	}
}
