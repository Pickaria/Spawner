package fr.pickaria.spawner.event

import org.bukkit.entity.Player
import org.bukkit.inventory.Merchant
import org.bukkit.inventory.MerchantInventory
import org.bukkit.inventory.MerchantRecipe

class PlayerBuyEvent(player: Player, merchant: Merchant, val trade: MerchantRecipe, val inventory: MerchantInventory): ResultShopEvent(player, merchant)