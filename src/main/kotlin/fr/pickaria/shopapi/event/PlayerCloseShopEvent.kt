package fr.pickaria.shopapi.event

import org.bukkit.entity.Player
import org.bukkit.inventory.Merchant
import org.bukkit.inventory.MerchantInventory

class PlayerCloseShopEvent(player: Player, merchant: Merchant, val inventory: MerchantInventory): ShopEvent(player, merchant)