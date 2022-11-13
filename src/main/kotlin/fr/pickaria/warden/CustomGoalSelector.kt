package fr.pickaria.warden

import net.minecraft.util.profiling.ProfilerFiller
import net.minecraft.world.entity.ai.goal.FleeSunGoal
import net.minecraft.world.entity.ai.goal.Goal
import net.minecraft.world.entity.ai.goal.GoalSelector
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
import org.bukkit.Bukkit
import java.util.function.Supplier

class CustomGoalSelector(profilerSupplier: Supplier<ProfilerFiller>?) : GoalSelector(profilerSupplier) {
	override fun tick() {

	}

	override fun tickRunningGoals(var0: Boolean) {

	}

	override fun addGoal(priority: Int, goal: Goal?) {
		Bukkit.broadcastMessage("$priority $goal")
	}
}