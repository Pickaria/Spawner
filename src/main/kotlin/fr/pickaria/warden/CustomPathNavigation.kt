package fr.pickaria.warden

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation
import net.minecraft.world.level.Level
import net.minecraft.world.level.pathfinder.PathFinder
import net.minecraft.world.phys.Vec3
import org.bukkit.Bukkit
import org.bukkit.Location

class CustomPathNavigation(mob: Mob?, level: Level?, location: Location) : GroundPathNavigation(mob, level) {
	private val target: BlockPos

	init {
		target = BlockPos(location.blockX, location.blockY, location.blockZ)

		createPath(target, 1)
	}

	override fun canUpdatePath(): Boolean {
		return true
	}

	override fun tick() {
		super.tick()

		createPath(target, 1)
	}
}