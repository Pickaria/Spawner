package fr.pickaria.spawner.event

import org.bukkit.entity.Player
import org.bukkit.inventory.Merchant
import org.bukkit.inventory.MerchantRecipe

class PlayerBuyEvent(player: Player, merchant: Merchant, val trade: MerchantRecipe): ResultShopEvent(player, merchant)