package fr.pickaria.shopapi

import fr.pickaria.shopapi.event.PlayerBuyEvent
import fr.pickaria.shopapi.event.PlayerCloseShopEvent
import fr.pickaria.shopapi.event.PlayerOpenShopEvent
import fr.pickaria.shopapi.event.PlayerSelectTradeEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.TradeSelectEvent
import org.bukkit.inventory.MerchantInventory

internal class Listeners : Listener {
	@EventHandler
	fun onPlayerOpenShop(event: InventoryOpenEvent) {
		with(event) {
			if (inventory.type == InventoryType.MERCHANT) {
				val merchantInventory = inventory as MerchantInventory
				if (merchantInventory.merchant is Shop) {
					val customEvent = PlayerOpenShopEvent(
						player as Player,
						merchantInventory.merchant as Shop
					)
					Bukkit.getPluginManager().callEvent(customEvent)
					isCancelled = customEvent.isCancelled
				}
			}
		}
	}

	@EventHandler
	fun onTradeSelect(event: TradeSelectEvent) {
		with(event) {
			if (merchant is Shop) {
				val customEvent = PlayerSelectTradeEvent(
					whoClicked as Player,
					merchant as Shop
				)
				Bukkit.getPluginManager().callEvent(customEvent)
				isCancelled = customEvent.isCancelled
			}
		}
	}

	@EventHandler
	fun onPlayerTrade(event: InventoryClickEvent) {
		with(event) {
			if (inventory.type == InventoryType.MERCHANT) {
				val merchantInventory = inventory as MerchantInventory
				if (merchantInventory.merchant is Shop) {
					val customEvent = PlayerBuyEvent(
						whoClicked as Player,
						merchantInventory.merchant as Shop
					)
					Bukkit.getPluginManager().callEvent(customEvent)
					isCancelled = customEvent.isCancelled
				}
			}
		}
	}

	@EventHandler
	fun onPlayerCloseShop(event: InventoryCloseEvent) {
		with(event) {
			if (inventory.type == InventoryType.MERCHANT) {
				val merchantInventory = inventory as MerchantInventory
				if (merchantInventory.merchant is Shop) {
					val customEvent = PlayerCloseShopEvent(
						player as Player,
						merchantInventory.merchant as Shop
					)
					Bukkit.getPluginManager().callEvent(customEvent)
				}
			}
		}
	}
}