package fr.pickaria.spawner

import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftMerchantCustom
import org.bukkit.inventory.MerchantRecipe

class Shop(title: String, private val customVillager: CustomVillager) : CraftMerchantCustom(title) {
	override fun setRecipes(recipes: MutableList<MerchantRecipe>) {
		super.setRecipes(recipes)
		customVillager.updateTrades()
	}

	override fun setRecipe(i: Int, merchantRecipe: MerchantRecipe) {
		super.setRecipe(i, merchantRecipe)
		customVillager.updateTrades()
	}

	val villager: org.bukkit.entity.Villager
		get() = customVillager.bukkitEntity as org.bukkit.entity.Villager
}