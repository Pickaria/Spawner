package fr.pickaria.spawner

import net.minecraft.server.level.ServerLevel
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld
import org.bukkit.entity.Villager

fun spawnVillager(location: Location, title: String): Villager {
	val world: ServerLevel = (location.world as CraftWorld).handle

	val villager = CustomVillager(location, title)

	villager.setPos(location.x, location.y, location.z)

	world.addFreshEntity(villager)

	return villager.bukkitEntity as Villager
}