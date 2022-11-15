package fr.pickaria.shopapi

import fr.pickaria.shopapi.event.PlayerInteractAtCustomVillagerEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.inventory.TradeSelectEvent
import org.bukkit.inventory.MerchantInventory

class Listeners : Listener {
	@EventHandler
	fun onPlayerOpenCustomMerchant(event: InventoryOpenEvent) {
		with(event) {
			if (inventory.type == InventoryType.MERCHANT) {
				val merchantInventory = inventory as MerchantInventory
				if (merchantInventory.merchant is Shop) {
					val customEvent = PlayerInteractAtCustomVillagerEvent(
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
	fun onPlayerInteractAtCustomVillager(event: PlayerInteractAtCustomVillagerEvent) {
		with(event) {
			isCancelled = true
			merchant.villager.shakeHead()
			/*val merchant = Shop(villager.name)

			val recipe = MerchantRecipe(ItemStack(Material.GRASS_BLOCK), Int.MAX_VALUE)
			recipe.addIngredient(ItemStack(Material.DIAMOND))
			merchant.recipes = listOf(recipe)

			player.openMerchant(merchant, true)*/
		}
	}

	@EventHandler
	fun onTrade(event: TradeSelectEvent) {
		if (event.merchant is Shop) {
			Bukkit.broadcastMessage("ok")
		}
	}
}