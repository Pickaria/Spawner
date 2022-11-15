package fr.pickaria.warden.event

import fr.pickaria.warden.Shop
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PlayerInteractAtCustomVillagerEvent(val player: Player, val merchant: Shop): Event(), Cancellable {
	companion object {
		private val HANDLERS = HandlerList()

		@JvmStatic
		fun getHandlerList() = HANDLERS
	}

	override fun getHandlers() = HANDLERS

	private var isCancelled = false

	override fun isCancelled() = isCancelled

	override fun setCancelled(isCancelled: Boolean) {
		this.isCancelled = isCancelled
	}
}