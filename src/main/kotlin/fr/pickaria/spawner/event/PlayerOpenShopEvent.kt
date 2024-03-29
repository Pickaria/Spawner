package fr.pickaria.spawner.event

import org.bukkit.entity.Player
import org.bukkit.entity.Villager
import org.bukkit.event.Cancellable
import org.bukkit.inventory.Merchant

class PlayerOpenShopEvent(player: Player, merchant: Merchant, val villager: Villager) : ShopEvent(player, merchant),
	Cancellable {
	private var isCancelled = false

	override fun isCancelled() = isCancelled

	override fun setCancelled(isCancelled: Boolean) {
		this.isCancelled = isCancelled
	}
}