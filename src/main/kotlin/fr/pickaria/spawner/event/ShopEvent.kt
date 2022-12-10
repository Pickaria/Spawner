package fr.pickaria.spawner.event

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.Merchant

abstract class ShopEvent(val player: Player, val merchant: Merchant): Event() {
	companion object {
		private val HANDLERS = HandlerList()

		@JvmStatic
		fun getHandlerList() = HANDLERS
	}

	override fun getHandlers() = HANDLERS
}