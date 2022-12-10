package fr.pickaria.spawner.event

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Merchant
import org.bukkit.inventory.MerchantRecipe

class ShopClickEvent(player: Player, merchant: Merchant, val trade: MerchantRecipe, val currentItem: ItemStack?): ResultShopEvent(player, merchant)