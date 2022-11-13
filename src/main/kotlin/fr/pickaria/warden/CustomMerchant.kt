package fr.pickaria.warden

import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftMerchantCustom
import org.bukkit.inventory.Merchant

class CustomMerchant(title: String): Merchant, CraftMerchantCustom(title) {
}