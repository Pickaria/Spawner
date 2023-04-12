package fr.pickaria.spawner

import fr.pickaria.spawner.event.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*
import org.bukkit.inventory.MerchantInventory

internal class Listeners : Listener {
	@EventHandler
	fun onPlayerOpenShop(event: InventoryOpenEvent) {
		with(event) {
			if (inventory.type == InventoryType.MERCHANT) {
				val merchantInventory = inventory as MerchantInventory
				if (merchantInventory.merchant is Shop) {
					val shop = merchantInventory.merchant as Shop

					val customEvent = PlayerOpenShopEvent(
						player as Player,
						shop,
						shop.villager
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
					merchant,
					merchant.getRecipe(index),
					inventory
				)
				Bukkit.getPluginManager().callEvent(customEvent)
				result = customEvent.result
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
					merchantInventory.selectedRecipe?.let {
						val customEvent: ResultShopEvent = if (slotType == InventoryType.SlotType.RESULT) {
							PlayerBuyEvent(
								whoClicked as Player,
								merchantInventory.merchant as Shop,
								it,
								merchantInventory
							)
						} else {
							ShopClickEvent(
								whoClicked as Player,
								merchantInventory.merchant as Shop,
								it,
								currentItem
							)
						}

						Bukkit.getPluginManager().callEvent(customEvent)
						result = customEvent.result
						isCancelled = customEvent.isCancelled
					}
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
						merchantInventory.merchant as Shop,
						merchantInventory
					)
					Bukkit.getPluginManager().callEvent(customEvent)
				}
			}
		}
	}
}