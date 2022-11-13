package fr.pickaria.warden

import net.minecraft.world.level.Level
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.entity.Player
import org.bukkit.event.entity.CreatureSpawnEvent

class Command: CommandExecutor {
	override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
		if (sender is Player) {
			val location = sender.location
			val villager = CustomVillager(location)

			val world: Level = (location.world as CraftWorld).handle
			world.addFreshEntity(villager, CreatureSpawnEvent.SpawnReason.DEFAULT)
		}

		return true
	}
}