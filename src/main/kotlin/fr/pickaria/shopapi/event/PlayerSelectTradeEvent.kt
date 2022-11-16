package fr.pickaria.shopapi.event

import org.bukkit.entity.Player
import org.bukkit.inventory.Merchant
import org.bukkit.inventory.MerchantInventory
import org.bukkit.inventory.MerchantRecipe

class PlayerSelectTradeEvent(
	player: Player,
	merchant: Merchant,
	val selectedRecipe: MerchantRecipe,
	val inventory: MerchantInventory
) : ResultShopEvent(player, merchant)