package fr.pickaria.shopapi

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import net.minecraft.network.Connection
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.PacketFlow
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R1.CraftServer
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.*

private class NetHandler(server: MinecraftServer?, connection: Connection?, player: ServerPlayer?) :
	ServerGamePacketListenerImpl(server, connection, player) {
	override fun send(packet: Packet<*>?) {}
}

fun spawnPlayer(location: Location, name: String): Player? {
	val server = (Bukkit.getServer() as CraftServer).server
	val world: ServerLevel = (location.world as CraftWorld).handle

	val profile = GameProfile(UUID.randomUUID(), name)

	// https://www.spigotmc.org/threads/nms-tutorials-3-gameprofiles.205493/
	// https://mineskin.org/
	val texture = "ewogICJ0aW1lc3RhbXAiIDogMTY2ODM1NDQ3NDQ5NCwKICAicHJvZmlsZUlkIiA6ICJmNThkZWJkNTlmNTA0MjIyOGY2MDIyMjExZDRjMTQwYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJ1bnZlbnRpdmV0YWxlbnQiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQ4Mjg1Mjk2MDFjZTMwZmJlYmQxYmRlMzlkMzU4NDI2N2QwOWFjZjVjMmZjNGJjZjA0ZGY4MDQ3Zjg4ZTljNSIKICAgIH0KICB9Cn0="
	val signature = "Lz83cnV1nrY8xNFSREmPAHTUWxNa6faK5laAMdk+QnKB+SwyENP77v8e3NeTBvwbMZV4wPtnIq/KQ5kAIGE4XQxeGrXcImtKZX1qd8hZ8VIQkOl0kZGt/Xpn6aE4RKIlwY5D2ixkuSZ4aewX/bgyO1g7XKRrot31qkaYiKCImySIAlbNDMOYTzB5Bwrh7+s8H4XHsTtN57I1wa5nfhVUL1khspll+o0KBasg1WzVC8ceUqd1C4SvpQNanAIVg8gRIu0Z7qo7rDtyXSIl6YTu12ewGFaWmry5DfDtIx/HUKJAN2OKzwTwx4W1X7XRsY4Ao2sYcENh7VYVHUvVmVniIVrH2UNfVjm2W0qMw58E3dDgqCxb+geFyXlT4YRaCL8+oBzIaanp3mZyl19jIb8oKL0q+hH38YEl8PZ4UFawPAo29yv3kO1Z+wGhN+1170p1aqFFquu/4TORjFx0ivozKqnsV77h56qAdrI02RlFZbP6rd4BmwsMtBihS5CtxWDTQYf2YJl7D7TN2ChW122B6T1XzAAs668rSB6XMDVPy6uA9BQBqZ4b8AB0J0Nr3JIfwT185fu9p09C1ewXOJQSD3GtApeg4PFxWBOiF8e80vAqNBcTj0R1jZYMa5DmqpKRmOVYL9a8J3MumENbsS0dxs693Gxib/kkD/DnSFcEfaw="
	profile.properties.put("textures", Property("textures", texture, signature))

	val player = CustomPlayer(server, world, profile, null)

	// https://www.spigotmc.org/threads/nms-serverplayer-entityplayer-for-the-1-17-1-18-mojang-mappings-with-fall-damage-and-knockback.551281/
	player.connection = NetHandler(server, Connection(PacketFlow.CLIENTBOUND), player)
	player.setPos(location.x, location.y, location.z)
	player.bukkitEntity.noDamageTicks = 0

	Bukkit.getOnlinePlayers().forEach {
		(it as CraftPlayer).handle.connection.send(
			ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, player)
		)
	}

	world.addNewPlayer(player)
	CustomPlayer.showAll(player, location)

	return player.bukkitEntity.player
}