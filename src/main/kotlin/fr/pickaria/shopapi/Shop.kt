package fr.pickaria.shopapi

import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftMerchantCustom
import org.bukkit.entity.Villager

class Shop(title: String, val villager: Villager) : CraftMerchantCustom(title)