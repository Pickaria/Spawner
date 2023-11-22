package fr.pickaria.spawner

import net.minecraft.server.level.ServerLevel
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld
import org.bukkit.entity.Villager
import java.util.*

fun spawnVillager(location: Location, title: String, uniqueId: UUID = UUID.randomUUID()): Villager {
	val world: ServerLevel = (location.world as CraftWorld).handle

	val villager = CustomVillager(location, title)

	villager.uuid = uniqueId
	villager.setPos(location.x, location.y, location.z)

	world.addFreshEntity(villager)

	return villager.bukkitEntity as Villager
}