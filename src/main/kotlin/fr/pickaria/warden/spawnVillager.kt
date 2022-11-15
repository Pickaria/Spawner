package fr.pickaria.warden

import net.minecraft.server.level.ServerLevel
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.entity.Villager

fun spawnVillager(location: Location): Villager {
	val world: ServerLevel = (location.world as CraftWorld).handle

	val villager = CustomVillager(location, "test")

	villager.setPos(location.x, location.y, location.z)

	world.addFreshEntity(villager)

	return villager.bukkitEntity as Villager
}