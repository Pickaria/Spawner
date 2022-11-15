package fr.pickaria.shopapi

import com.mojang.authlib.GameProfile
import net.minecraft.commands.arguments.EntityAnchorArgument
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.ai.targeting.TargetingConditions
import net.minecraft.world.entity.player.ProfilePublicKey
import net.minecraft.world.level.levelgen.structure.BoundingBox
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer
import org.bukkit.entity.Player


class CustomPlayer(
	server: MinecraftServer,
	world: ServerLevel,
	profile: GameProfile,
	profilePublicKey: ProfilePublicKey?
) : ServerPlayer(server, world, profile, profilePublicKey) {
	companion object {
		fun showAll(entityPlayer: ServerPlayer, location: Location) {
			val playerInfoAdd = ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, entityPlayer)
			val namedEntitySpawn = ClientboundAddPlayerPacket(entityPlayer)
			val headRotation = ClientboundRotateHeadPacket(
				entityPlayer,
				(location.yaw * 256f / 360f).toInt().toByte()
			)
			val playerInfoRemove = ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, entityPlayer)

			for (player in Bukkit.getOnlinePlayers()) {
				val connection = (player as CraftPlayer).handle.connection
				connection.send(playerInfoAdd)
				connection.send(namedEntitySpawn)
				connection.send(headRotation)
				connection.send(playerInfoRemove)
			}

			entityPlayer.entityData[DATA_PLAYER_MODE_CUSTOMISATION] = 0xFF.toByte()
		}

		fun show(entityPlayer: ServerPlayer, player: Player) {
			val playerInfoAdd = ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, entityPlayer)
			val namedEntitySpawn = ClientboundAddPlayerPacket(entityPlayer)
			val playerInfoRemove = ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, entityPlayer)

				val connection = (player as CraftPlayer).handle.connection
				connection.send(playerInfoAdd)
				connection.send(namedEntitySpawn)
				connection.send(playerInfoRemove)

			entityPlayer.entityData[DATA_PLAYER_MODE_CUSTOMISATION] = 0xFF.toByte()
		}
	}

	private fun of(center: Vec3, x: Double, y: Double, z: Double): BoundingBox =
		BoundingBox(
			(center.x - x).toInt(),
			(center.y - y).toInt(),
			(center.z - z).toInt(),
			(center.x + x).toInt(),
			(center.y + y).toInt(),
			(center.z + z).toInt()
		)

	override fun tick() {
		super.tick()

		// Look at nearest player
		val boundingBox = of(position(), 5.0, 5.0, 5.0)

		val aabb = AABB.of(boundingBox)
		level.getNearestEntity(
			ServerPlayer::class.java,
			TargetingConditions.forNonCombat(),
			this,
			position().x,
			position().y,
			position().z,
			aabb
		)?.let {
			lookAt(EntityAnchorArgument.Anchor.EYES, it, EntityAnchorArgument.Anchor.EYES)
		}
	}

	override fun interactOn(entity: Entity?, enumhand: InteractionHand?): InteractionResult {
		Bukkit.broadcastMessage("test")
		return super.interactOn(entity, enumhand)
	}

	// Make invisible
	override fun hurt(damagesource: DamageSource?, f: Float): Boolean = false
}