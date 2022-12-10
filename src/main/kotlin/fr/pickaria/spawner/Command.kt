package fr.pickaria.spawner

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.entity.Villager
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Merchant
import org.bukkit.inventory.MerchantRecipe

internal class Command : CommandExecutor {
	override fun onCommand(sender: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
		if (sender is Player) {
			val location = sender.location

			spawnVillager(location, "test").let {
				it.profession = Villager.Profession.ARMORER
				it.villagerType = Villager.Type.JUNGLE

				val merchant = it as Merchant
				val recipe = MerchantRecipe(ItemStack(Material.GRASS_BLOCK), Int.MAX_VALUE)
				recipe.addIngredient(ItemStack(Material.DIAMOND))
				val recipe2 = MerchantRecipe(ItemStack(Material.LAPIS_BLOCK), Int.MAX_VALUE)
				recipe2.addIngredient(ItemStack(Material.REDSTONE))
				merchant.recipes = listOf(recipe, recipe2)

				it.customName = "Test"

				sender.sendMessage(it.recipeCount.toString())

//				it.villagerExperience = 1
//				it.villagerLevel = 1
			}
		}

		return true
	}
}