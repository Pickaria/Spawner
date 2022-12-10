package fr.pickaria.spawner.event

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.inventory.Merchant

abstract class ResultShopEvent(player: Player, merchant: Merchant): ShopEvent(player, merchant), Cancellable {
	companion object {
		private val HANDLERS = HandlerList()

		@JvmStatic
		fun getHandlerList() = HANDLERS
	}

	override fun getHandlers() = HANDLERS
	var result: Result = Result.DEFAULT

	private var isCancelled = false

	override fun isCancelled() = isCancelled

	override fun setCancelled(isCancelled: Boolean) {
		this.isCancelled = isCancelled
	}
}