package fr.pickaria.warden

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Command : CommandExecutor {

	override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
		if (sender is Player) {
			val location = sender.location

			spawnPlayer(location, "Bernard")?.let {
				sender.sendMessage(it.toString())
//				sender.hidePlayer(plugin, it)

				it.inventory.setItemInMainHand(ItemStack(Material.DIAMOND_SWORD))
			}
		}

		return true
	}
}