package fr.pickaria.warden

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.TradeSelectEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.MerchantRecipe

class Listeners : Listener {
	@EventHandler
	fun on(event: PlayerInteractAtEntityEvent) {
		with(event) {
			if ((rightClicked as CraftPlayer).handle is CustomPlayer) {
				val merchant = Shop(rightClicked.name)

				val recipe = MerchantRecipe(ItemStack(Material.GRASS_BLOCK), Int.MAX_VALUE)
				recipe.addIngredient(ItemStack(Material.DIAMOND))
				merchant.recipes = listOf(recipe)

				player.openMerchant(merchant, true)
			}
		}
	}

	@EventHandler
	fun onTrade(event: TradeSelectEvent) {
		if (event.merchant is Shop) {
			Bukkit.broadcastMessage("ok")
		}
	}
}