package fr.pickaria.warden

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.goal.FleeSunGoal
import net.minecraft.world.entity.ai.goal.Goal
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.entity.npc.VillagerProfession
import net.minecraft.world.phys.Vec3
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import kotlin.random.Random


class CustomVillager(location: Location) :
	Villager(EntityType.VILLAGER, (location.world as CraftWorld).handle) {

	private val target: Vec3
	private val maxDist: Int = 5

	init {
		val level = (location.world as CraftWorld).handle
		target = Vec3(location.blockX.toDouble(), location.blockY.toDouble(), location.blockZ.toDouble())

		this.setPos(location.x, location.y, location.z)

		this.goalSelector.disableControlFlag(Goal.Flag.MOVE)
		this.targetSelector.disableControlFlag(Goal.Flag.MOVE)

		//this.goalSelector = CustomGoalSelector(level.profilerSupplier)
		//this.goalSelector.addGoal(1, FleeSunGoal(this.navigation.path))

		//this.navigation = CustomPathNavigation(this, level, location)
		//(this.navigation as GroundPathNavigation).setCanOpenDoors(true)
		//this.navigation.setCanFloat(true)
	}

	override fun tick() {
		super.tick()

		if (navigation.isDone) {
			val x = target.x + Random.Default.nextInt(-maxDist, maxDist)
			val y = target.y + Random.Default.nextInt(-maxDist, maxDist)
			val z = target.z + Random.Default.nextInt(-maxDist, maxDist)
			val target = BlockPos(x, y, z)
			val path = this.navigation.createPath(target, 0)
			this.navigation.moveTo(path, 0.5)
		}
	}
}
