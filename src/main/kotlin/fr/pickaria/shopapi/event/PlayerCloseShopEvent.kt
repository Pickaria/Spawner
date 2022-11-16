package fr.pickaria.shopapi.event

import fr.pickaria.shopapi.Shop
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerCloseShopEvent(val player: Player, val merchant: Shop): Event() {
	companion object {
		private val HANDLERS = HandlerList()

		@JvmStatic
		fun getHandlerList() = HANDLERS
	}

	override fun getHandlers() = HANDLERS
}