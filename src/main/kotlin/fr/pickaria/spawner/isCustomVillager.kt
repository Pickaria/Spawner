package fr.pickaria.spawner

import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity
import org.bukkit.entity.Entity

fun isCustomVillager(entity: Entity) = (entity as CraftEntity).handle is CustomVillager
