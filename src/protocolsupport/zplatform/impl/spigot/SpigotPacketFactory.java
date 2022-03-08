package protocolsupport.zplatform.impl.spigot;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;

import com.mojang.authlib.GameProfile;

import io.netty.buffer.Unpooled;
import net.minecraft.network.EnumProtocol;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent.ChatSerializer;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.protocol.game.PacketPlayInFlying.PacketPlayInLook;
import net.minecraft.network.protocol.game.PacketPlayInFlying.PacketPlayInPosition;
import net.minecraft.network.protocol.game.PacketPlayInFlying.PacketPlayInPositionLook;
import net.minecraft.network.protocol.game.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.network.protocol.game.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import net.minecraft.network.protocol.game.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook;
import net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol;
import net.minecraft.network.protocol.login.PacketLoginInCustomPayload;
import net.minecraft.network.protocol.login.PacketLoginInEncryptionBegin;
import net.minecraft.network.protocol.login.PacketLoginInStart;
import net.minecraft.network.protocol.login.PacketLoginOutCustomPayload;
import net.minecraft.network.protocol.login.PacketLoginOutDisconnect;
import net.minecraft.network.protocol.login.PacketLoginOutEncryptionBegin;
import net.minecraft.network.protocol.login.PacketLoginOutSetCompression;
import net.minecraft.network.protocol.login.PacketLoginOutSuccess;
import net.minecraft.network.protocol.status.PacketStatusInPing;
import net.minecraft.network.protocol.status.PacketStatusInStart;
import net.minecraft.network.protocol.status.PacketStatusOutPong;
import net.minecraft.network.protocol.status.PacketStatusOutServerInfo;
import net.minecraft.network.protocol.status.ServerPing;
import net.minecraft.network.protocol.status.ServerPing.ServerData;
import net.minecraft.network.protocol.status.ServerPing.ServerPingPlayerSample;
import net.minecraft.resources.MinecraftKey;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.events.ServerPingResponseEvent.ProtocolInfo;
import protocolsupport.api.utils.Profile;
import protocolsupport.utils.reflection.FieldReader;
import protocolsupport.utils.reflection.ReflectionUtils;
import protocolsupport.utils.reflection.UncheckedReflectionException;
import protocolsupport.zplatform.PlatformPacketFactory;

public class SpigotPacketFactory implements PlatformPacketFactory {

	@Override
	public Object createOutboundChatPacket(String message, int position, UUID uuid) {
		return new PacketPlayOutChat(ChatSerializer.a(message), ChatMessageType.a((byte) position), uuid);
	}

	protected static final BaseComponent emptyMessage = new TextComponent("");

	@Override
	public Object createTabHeaderFooterPacket(BaseComponent header, BaseComponent footer) {
		return new PacketPlayOutPlayerListHeaderFooter(
			ChatSerializer.a(ChatAPI.toJSON(header != null ? header : emptyMessage)),
			ChatSerializer.a(ChatAPI.toJSON(footer != null ? footer : emptyMessage))
		);
	}

	@Override
	public Object createTitleResetPacket() {
		return new ClientboundClearTitlesPacket(true);
	}

	@Override
	public Object createTitleClearPacket() {
		return new ClientboundClearTitlesPacket(false);
	}

	@Override
	public Object createTitleMainPacket(String title) {
		return new ClientboundSetTitleTextPacket(ChatSerializer.a(title));
	}

	@Override
	public Object createTitleSubPacket(String title) {
		return new ClientboundSetSubtitleTextPacket(ChatSerializer.a(title));
	}

	@Override
	public Object createTitleParamsPacket(int fadeIn, int stay, int fadeOut) {
		return new ClientboundSetTitlesAnimationPacket(fadeIn, stay, fadeOut);
	}

	@Override
	public Object createLoginDisconnectPacket(BaseComponent message) {
		return new PacketLoginOutDisconnect(SpigotMiscUtils.toPlatformMessage(message));
	}

	@Override
	public Object createPlayDisconnectPacket(BaseComponent message) {
		return new PacketPlayOutKickDisconnect(SpigotMiscUtils.toPlatformMessage(message));
	}

	@Override
	public Object createLoginEncryptionBeginPacket(byte[] publicKey, byte[] randomBytes) {
		return new PacketLoginOutEncryptionBegin("", publicKey, randomBytes);
	}

	@Override
	public Object createSetCompressionPacket(int threshold) {
		return new PacketLoginOutSetCompression(threshold);
	}

	@Override
	public Object createStatusPongPacket(long pingId) {
		return new PacketStatusOutPong(pingId);
	}

	private final UUID profileUUID = UUID.randomUUID();
	@Override
	public Object createStatusServerInfoPacket(List<String> profiles, ProtocolInfo info, String icon, BaseComponent motd, int onlinePlayers, int maxPlayers) {
		ServerPingPlayerSample playerSample = new ServerPingPlayerSample(maxPlayers, onlinePlayers);

		com.mojang.authlib.GameProfile[] gprofiles = new com.mojang.authlib.GameProfile[profiles.size()];
		for (int i = 0; i < profiles.size(); i++) {
			gprofiles[i] = new com.mojang.authlib.GameProfile(profileUUID, profiles.get(i));
		}
		playerSample.a(gprofiles);

		ServerPing serverping = new ServerPing();
		serverping.a(icon);
		serverping.a(SpigotMiscUtils.toPlatformMessage(motd));
		serverping.a(playerSample);
		serverping.a(new ServerData(info.getName(), info.getId()));

		return new PacketStatusOutServerInfo(serverping);
	}

	@Override
	public Object createLoginSuccessPacket(Profile profile) {
		return new PacketLoginOutSuccess(new GameProfile(profile.getUUID(), profile.getName()));
	}

	protected static final PacketDataSerializer emptyPDS = new PacketDataSerializer(Unpooled.EMPTY_BUFFER);
	@Override
	public Object createEmptyCustomPayloadPacket(String tag) {
		return new PacketPlayOutCustomPayload(new MinecraftKey("ps", tag), emptyPDS);
	}

	@Override
	public Object createFakeJoinGamePacket() {
		throw new UnsupportedOperationException("Fake join game packet is not implemented yet");
	}

	@Override
	public Object createEntityStatusPacket(org.bukkit.entity.Entity entity, int status) {
		return new PacketPlayOutEntityStatus(((CraftEntity) entity).getHandle(), (byte) status);
	}


	@Override
	public int getOutLoginDisconnectPacketId() {
		return getOutId(PacketLoginOutDisconnect.class);
	}

	@Override
	public int getOutLoginEncryptionBeginPacketId() {
		return getOutId(PacketLoginOutEncryptionBegin.class);
	}

	@Override
	public int getOutLoginSuccessPacketId() {
		return getOutId(PacketLoginOutSuccess.class);
	}

	@Override
	public int getOutLoginSetCompressionPacketId() {
		return getOutId(PacketLoginOutSetCompression.class);
	}

	@Override
	public int getOutLoginCustomPayloadPacketId() {
		return getOutId(PacketLoginOutCustomPayload.class);
	}

	@Override
	public int getOutStatusServerInfoPacketId() {
		return getOutId(PacketStatusOutServerInfo.class);
	}

	@Override
	public int getOutStatusPongPacketId() {
		return getOutId(PacketStatusOutPong.class);
	}

	@Override
	public int getOutPlayKeepAlivePacketId() {
		return getOutId(PacketPlayOutKeepAlive.class);
	}

	@Override
	public int getOutPlayLoginPacketId() {
		return getOutId(PacketPlayOutLogin.class);
	}

	@Override
	public int getOutPlayChatPacketId() {
		return getOutId(PacketPlayOutChat.class);
	}

	@Override
	public int getOutPlayUpdateTimePacketId() {
		return getOutId(PacketPlayOutUpdateTime.class);
	}

	@Override
	public int getOutPlayEntityEquipmentPacketId() {
		return getOutId(PacketPlayOutEntityEquipment.class);
	}

	@Override
	public int getOutPlaySpawnPositionPacketId() {
		return getOutId(PacketPlayOutSpawnPosition.class);
	}

	@Override
	public int getOutPlayUpdateHealthPacketId() {
		return getOutId(PacketPlayOutUpdateHealth.class);
	}

	@Override
	public int getOutPlayRespawnPacketId() {
		return getOutId(PacketPlayOutRespawn.class);
	}

	@Override
	public int getOutPlayPositionPacketId() {
		return getOutId(PacketPlayOutPosition.class);
	}

	@Override
	public int getOutPlayHeldSlotPacketId() {
		return getOutId(PacketPlayOutHeldItemSlot.class);
	}

	@Override
	public int getOutPlayAnimationPacketId() {
		return getOutId(PacketPlayOutAnimation.class);
	}

	@Override
	public int getOutPlaySpawnNamedPacketId() {
		return getOutId(PacketPlayOutNamedEntitySpawn.class);
	}

	@Override
	public int getOutPlayCollectEffectPacketId() {
		return getOutId(PacketPlayOutCollect.class);
	}

	@Override
	public int getOutPlaySpawnObjectPacketId() {
		return getOutId(PacketPlayOutSpawnEntity.class);
	}

	@Override
	public int getOutPlaySpawnLivingPacketId() {
		return getOutId(PacketPlayOutSpawnEntityLiving.class);
	}

	@Override
	public int getOutPlaySpawnPaintingPacketId() {
		return getOutId(PacketPlayOutSpawnEntityPainting.class);
	}

	@Override
	public int getOutPlaySpawnExpOrbPacketId() {
		return getOutId(PacketPlayOutSpawnEntityExperienceOrb.class);
	}

	@Override
	public int getOutPlayEntityVelocityPacketId() {
		return getOutId(PacketPlayOutEntityVelocity.class);
	}

	@Override
	public int getOutPlayEntityDestroyPacketId() {
		return getOutId(PacketPlayOutEntityDestroy.class);
	}

	@Override
	public int getOutPlayEntityRelMovePacketId() {
		return getOutId(PacketPlayOutRelEntityMove.class);
	}

	@Override
	public int getOutPlayEntityLookPacketId() {
		return getOutId(PacketPlayOutEntityLook.class);
	}

	@Override
	public int getOutPlayEntityRelMoveLookPacketId() {
		return getOutId(PacketPlayOutRelEntityMoveLook.class);
	}

	@Override
	public int getOutPlayEntityTeleportPacketId() {
		return getOutId(PacketPlayOutEntityTeleport.class);
	}

	@Override
	public int getOutPlayEntityHeadRotationPacketId() {
		return getOutId(PacketPlayOutEntityHeadRotation.class);
	}

	@Override
	public int getOutPlayEntityStatusPacketId() {
		return getOutId(PacketPlayOutEntityStatus.class);
	}

	@Override
	public int getOutPlayEntityLeashPacketId() {
		return getOutId(PacketPlayOutAttachEntity.class);
	}

	@Override
	public int getOutPlayEntityMetadataPacketId() {
		return getOutId(PacketPlayOutEntityMetadata.class);
	}

	@Override
	public int getOutPlayEntityEffectAddPacketId() {
		return getOutId(PacketPlayOutEntityEffect.class);
	}

	@Override
	public int getOutPlayEntityEffectRemovePacketId() {
		return getOutId(PacketPlayOutRemoveEntityEffect.class);
	}

	@Override
	public int getOutPlayEntitySoundPacketId() {
		return getOutId(PacketPlayOutEntitySound.class);
	}

	@Override
	public int getOutPlayExperiencePacketId() {
		return getOutId(PacketPlayOutExperience.class);
	}

	@Override
	public int getOutPlayEntityAttributesPacketId() {
		return getOutId(PacketPlayOutUpdateAttributes.class);
	}

	@Override
	public int getOutPlayChunkDataPacketId() {
		return getOutId(ClientboundLevelChunkWithLightPacket.class);
	}

	@Override
	public int getOutPlayBlockChangeMultiPacketId() {
		return getOutId(PacketPlayOutMultiBlockChange.class);
	}

	@Override
	public int getOutPlayBlockChangeSinglePacketId() {
		return getOutId(PacketPlayOutBlockChange.class);
	}

	@Override
	public int getOutPlayBlockActionPacketId() {
		return getOutId(PacketPlayOutBlockAction.class);
	}

	@Override
	public int getOutPlayBlockBreakAnimationPacketId() {
		return getOutId(PacketPlayOutBlockBreakAnimation.class);
	}

	@Override
	public int getOutPlayExplosionPacketId() {
		return getOutId(PacketPlayOutExplosion.class);
	}

	@Override
	public int getOutPlayWorldEventPacketId() {
		return getOutId(PacketPlayOutWorldEvent.class);
	}

	@Override
	public int getOutPlayWorldSoundPacketId() {
		return getOutId(PacketPlayOutNamedSoundEffect.class);
	}

	@Override
	public int getOutPlayWorldParticlesPacketId() {
		return getOutId(PacketPlayOutWorldParticles.class);
	}

	@Override
	public int getOutPlayGameStateChangePacketId() {
		return getOutId(PacketPlayOutGameStateChange.class);
	}

	@Override
	public int getOutPlayWindowOpenPacketId() {
		return getOutId(PacketPlayOutOpenWindow.class);
	}

	@Override
	public int getOutPlayWindowHorseOpenPacketId() {
		return getOutId(PacketPlayOutOpenWindowHorse.class);
	}

	@Override
	public int getOutPlayWindowClosePacketId() {
		return getOutId(PacketPlayOutCloseWindow.class);
	}

	@Override
	public int getOutPlayWindowSetSlotPacketId() {
		return getOutId(PacketPlayOutSetSlot.class);
	}

	@Override
	public int getOutPlayWindowSetItemsPacketId() {
		return getOutId(PacketPlayOutWindowItems.class);
	}

	@Override
	public int getOutPlayWindowDataPacketId() {
		return getOutId(PacketPlayOutWindowData.class);
	}

	@Override
	public int getOutPlayMapPacketId() {
		return getOutId(PacketPlayOutMap.class);
	}

	@Override
	public int getOutPlayUpdateTilePacketId() {
		return getOutId(PacketPlayOutTileEntityData.class);
	}

	@Override
	public int getOutPlaySignEditorPacketId() {
		return getOutId(PacketPlayOutOpenSignEditor.class);
	}

	@Override
	public int getOutPlayStatisticsPacketId() {
		return getOutId(PacketPlayOutStatistic.class);
	}

	@Override
	public int getOutPlayPlayerInfoPacketId() {
		return getOutId(PacketPlayOutPlayerInfo.class);
	}

	@Override
	public int getOutPlayAbilitiesPacketId() {
		return getOutId(PacketPlayOutAbilities.class);
	}

	@Override
	public int getOutPlayTabCompletePacketId() {
		return getOutId(PacketPlayOutTabComplete.class);
	}

	@Override
	public int getOutPlayScoreboardObjectivePacketId() {
		return getOutId(PacketPlayOutScoreboardObjective.class);
	}

	@Override
	public int getOutPlayScoreboardScorePacketId() {
		return getOutId(PacketPlayOutScoreboardScore.class);
	}

	@Override
	public int getOutPlayScoreboardDisplaySlotPacketId() {
		return getOutId(PacketPlayOutScoreboardDisplayObjective.class);
	}

	@Override
	public int getOutPlayScoreboardTeamPacketId() {
		return getOutId(PacketPlayOutScoreboardTeam.class);
	}

	@Override
	public int getOutPlayCustomPayloadPacketId() {
		return getOutId(PacketPlayOutCustomPayload.class);
	}

	@Override
	public int getOutPlayKickDisconnectPacketId() {
		return getOutId(PacketPlayOutKickDisconnect.class);
	}

	@Override
	public int getOutPlayResourcePackPacketId() {
		return getOutId(PacketPlayOutResourcePackSend.class);
	}

	@Override
	public int getOutPlayCameraPacketId() {
		return getOutId(PacketPlayOutCamera.class);
	}

	@Override
	public int getOutPlayPlayerListHeaderFooterPacketId() {
		return getOutId(PacketPlayOutPlayerListHeaderFooter.class);
	}

	@Override
	public int getOutPlaySetPassengersPacketId() {
		return getOutId(PacketPlayOutMount.class);
	}

	@Override
	public int getOutPlayChunkUnloadPacketId() {
		return getOutId(PacketPlayOutUnloadChunk.class);
	}

	@Override
	public int getOutPlayWorldCustomSoundPacketId() {
		return getOutId(PacketPlayOutCustomSoundEffect.class);
	}

	@Override
	public int getOutPlayServerDifficultyPacketId() {
		return getOutId(PacketPlayOutServerDifficulty.class);
	}

	@Override
	public int getOutPlayBossBarPacketId() {
		return getOutId(PacketPlayOutBoss.class);
	}

	@Override
	public int getOutPlaySetCooldownPacketId() {
		return getOutId(PacketPlayOutSetCooldown.class);
	}

	@Override
	public int getOutPlayVehicleMovePacketId() {
		return getOutId(PacketPlayOutVehicleMove.class);
	}

	@Override
	public int getOutPlayUnlockRecipesPacketId() {
		return getOutId(PacketPlayOutRecipes.class);
	}

	@Override
	public int getOutPlayAdvancementsPacketId() {
		return getOutId(PacketPlayOutAdvancements.class);
	}

	@Override
	public int getOutPlayAdvancementsTabPacketId() {
		return getOutId(PacketPlayOutSelectAdvancementTab.class);
	}

	@Override
	public int getOutPlayCraftRecipeConfirmPacketId() {
		return getOutId(PacketPlayOutAutoRecipe.class);
	}

	@Override
	public int getOutPlayDeclareCommandsPacketId() {
		return getOutId(PacketPlayOutCommands.class);
	}

	@Override
	public int getOutPlayDeclareRecipesPacketId() {
		return getOutId(PacketPlayOutRecipeUpdate.class);
	}

	@Override
	public int getOutPlayDeclareTagsPacket() {
		return getOutId(PacketPlayOutTags.class);
	}

	@Override
	public int getOutPlayQueryNBTResponsePacketId() {
		return getOutId(PacketPlayOutNBTQuery.class);
	}

	@Override
	public int getOutPlayStopSoundPacketId() {
		return getOutId(PacketPlayOutStopSound.class);
	}

	@Override
	public int getOutPlayLookAtPacketId() {
		return getOutId(PacketPlayOutLookAt.class);
	}

	@Override
	public int getOutPlayChunkLightPacketId() {
		return getOutId(PacketPlayOutLightUpdate.class);
	}

	@Override
	public int getOutPlaySetViewCenterPacketId() {
		return getOutId(PacketPlayOutViewCentre.class);
	}

	@Override
	public int getOutPlayMerchantTradeListPacketId() {
		return getOutId(PacketPlayOutOpenWindowMerchant.class);
	}

	@Override
	public int getOutPlayUpdateViewDistancePacketId() {
		return getOutId(PacketPlayOutViewDistance.class);
	}

	@Override
	public int getOutPlayUpdateSimulationDistancePacketId() {
		return getOutId(ClientboundSetSimulationDistancePacket.class);
	}

	@Override
	public int getOutPlayBookOpenPacketId() {
		return getOutId(PacketPlayOutOpenBook.class);
	}

	@Override
	public int getOutPlayAcknowledgePlayerDiggingPacketId() {
		return getOutId(PacketPlayOutBlockBreak.class);
	}

	@Override
	public int getOutPlayTitleTextPacketId() {
		return getOutId(ClientboundSetTitleTextPacket.class);
	}

	@Override
	public int getOutPlayTitleSubTextPacketId() {
		return getOutId(ClientboundSetSubtitleTextPacket.class);
	}

	@Override
	public int getOutPlayTitleAnimationPacketId() {
		return getOutId(ClientboundSetTitlesAnimationPacket.class);
	}

	@Override
	public int getOutPlayTitleClearPacketId() {
		return getOutId(ClientboundClearTitlesPacket.class);
	}

	@Override
	public int getOutPlayActionbarPacketId() {
		return getOutId(ClientboundSetActionBarTextPacket.class);
	}

	@Override
	public int getOutPlayWorldborderInitPacketId() {
		return getOutId(ClientboundInitializeBorderPacket.class);
	}

	@Override
	public int getOutPlayWorldborderCenterPacketId() {
		return getOutId(ClientboundSetBorderCenterPacket.class);
	}

	@Override
	public int getOutPlayWorldborderLerpSizePacketId() {
		return getOutId(ClientboundSetBorderLerpSizePacket.class);
	}

	@Override
	public int getOutPlayWorldborderSizePacketId() {
		return getOutId(ClientboundSetBorderSizePacket.class);
	}

	@Override
	public int getOutPlayWorldborderWarnDelayPacketId() {
		return getOutId(ClientboundSetBorderWarningDelayPacket.class);
	}

	@Override
	public int getOutPlayWorldborderWarnDistancePacketId() {
		return getOutId(ClientboundSetBorderWarningDistancePacket.class);
	}

	@Override
	public int getOutPlayCombatBeginPacketId() {
		return getOutId(ClientboundPlayerCombatEnterPacket.class);
	}

	@Override
	public int getOutPlayCombatEndPacketId() {
		return getOutId(ClientboundPlayerCombatEndPacket.class);
	}

	@Override
	public int getOutPlayCombatDeathPacketId() {
		return getOutId(ClientboundPlayerCombatKillPacket.class);
	}

	@Override
	public int getOutPlayVibration() {
		return getOutId(ClientboundAddVibrationSignalPacket.class);
	}

	@Override
	public int getOutPlaySyncPing() {
		return getOutId(ClientboundPingPacket.class);
	}


	@Override
	public int getInHandshakeStartPacketId() {
		return getInId(PacketHandshakingInSetProtocol.class);
	}

	@Override
	public int getInStatusRequestPacketId() {
		return getInId(PacketStatusInStart.class);
	}

	@Override
	public int getInStatusPingPacketId() {
		return getInId(PacketStatusInPing.class);
	}

	@Override
	public int getInLoginStartPacketId() {
		return getInId(PacketLoginInStart.class);
	}

	@Override
	public int getInLoginEncryptionBeginPacketId() {
		return getInId(PacketLoginInEncryptionBegin.class);
	}

	@Override
	public int getInLoginCustomPayloadPacketId() {
		return getInId(PacketLoginInCustomPayload.class);
	}

	@Override
	public int getInPlayKeepAlivePacketId() {
		return getInId(PacketPlayInKeepAlive.class);
	}

	@Override
	public int getInPlayChatPacketId() {
		return getInId(PacketPlayInChat.class);
	}

	@Override
	public int getInPlayUseEntityPacketId() {
		return getInId(PacketPlayInUseEntity.class);
	}

	@Override
	public int getInPlayPositionPacketId() {
		return getInId(PacketPlayInPosition.class);
	}

	@Override
	public int getInPlayLookPacketId() {
		return getInId(PacketPlayInLook.class);
	}

	@Override
	public int getInPlayPositionLookPacketId() {
		return getInId(PacketPlayInPositionLook.class);
	}

	@Override
	public int getInPlayGroundPacketId() {
		return getInId(PacketPlayInFlying.d.class);
	}

	@Override
	public int getInPlayBlockDigPacketId() {
		return getInId(PacketPlayInBlockDig.class);
	}

	@Override
	public int getInPlayBlockPlacePacketId() {
		return getInId(PacketPlayInBlockPlace.class);
	}

	@Override
	public int getInPlayHeldSlotPacketId() {
		return getInId(PacketPlayInHeldItemSlot.class);
	}

	@Override
	public int getInPlayAnimationPacketId() {
		return getInId(PacketPlayInArmAnimation.class);
	}

	@Override
	public int getInPlayEntityActionPacketId() {
		return getInId(PacketPlayInEntityAction.class);
	}

	@Override
	public int getInPlayMoveVehiclePacketId() {
		return getInId(PacketPlayInVehicleMove.class);
	}

	@Override
	public int getInPlaySteerBoatPacketId() {
		return getInId(PacketPlayInBoatMove.class);
	}

	@Override
	public int getInPlaySteerVehiclePacketId() {
		return getInId(PacketPlayInSteerVehicle.class);
	}

	@Override
	public int getInPlayWindowClosePacketId() {
		return getInId(PacketPlayInCloseWindow.class);
	}

	@Override
	public int getInPlayWindowClickPacketId() {
		return getInId(PacketPlayInWindowClick.class);
	}

	@Override
	public int getInPlayCreativeSetSlotPacketId() {
		return getInId(PacketPlayInSetCreativeSlot.class);
	}

	@Override
	public int getInPlayEnchantSelectPacketId() {
		return getInId(PacketPlayInEnchantItem.class);
	}

	@Override
	public int getInPlayUpdateSignPacketId() {
		return getInId(PacketPlayInUpdateSign.class);
	}

	@Override
	public int getInPlayAbilitiesPacketId() {
		return getInId(PacketPlayInAbilities.class);
	}

	@Override
	public int getInPlayTabCompletePacketId() {
		return getInId(PacketPlayInTabComplete.class);
	}

	@Override
	public int getInPlaySettingsPacketId() {
		return getInId(PacketPlayInSettings.class);
	}

	@Override
	public int getInPlayClientCommandPacketId() {
		return getInId(PacketPlayInClientCommand.class);
	}

	@Override
	public int getInPlayCustomPayloadPacketId() {
		return getInId(PacketPlayInCustomPayload.class);
	}

	@Override
	public int getInPlayUseItemPacketId() {
		return getInId(PacketPlayInUseItem.class);
	}

	@Override
	public int getInPlaySpectatePacketId() {
		return getInId(PacketPlayInSpectate.class);
	}

	@Override
	public int getInPlayResourcePackStatusPacketId() {
		return getInId(PacketPlayInResourcePackStatus.class);
	}

	@Override
	public int getInPlayTeleportAcceptPacketId() {
		return getInId(PacketPlayInTeleportAccept.class);
	}

	@Override
	public int getInPlayRecipeBookRecipePacketId() {
		return getInId(PacketPlayInRecipeDisplayed.class);
	}

	@Override
	public int getInPlayRecipeBookStatePacketId() {
		return getInId(PacketPlayInRecipeSettings.class);
	}

	@Override
	public int getInPlayCraftRecipeRequestPacketId() {
		return getInId(PacketPlayInAutoRecipe.class);
	}

	@Override
	public int getInPlayAdvancementTabPacketId() {
		return getInId(PacketPlayInAdvancements.class);
	}

	@Override
	public int getInPlayQueryBlockNBTPacketId() {
		return getInId(PacketPlayInTileNBTQuery.class);
	}

	@Override
	public int getInPlayQueryEntityNBTPacketId() {
		return getInId(PacketPlayInEntityNBTQuery.class);
	}

	@Override
	public int getInPlayEditBookPacketId() {
		return getInId(PacketPlayInBEdit.class);
	}

	@Override
	public int getInPlayPickItemPacketId() {
		return getInId(PacketPlayInPickItem.class);
	}

	@Override
	public int getInPlayNameItemPacketId() {
		return getInId(PacketPlayInItemName.class);
	}

	@Override
	public int getInPlaySelectTradePacketId() {
		return getInId(PacketPlayInTrSel.class);
	}

	@Override
	public int getInPlaySetBeaconEffectPacketId() {
		return getInId(PacketPlayInBeacon.class);
	}

	@Override
	public int getInPlayUpdateCommandBlockPacketId() {
		return getInId(PacketPlayInSetCommandBlock.class);
	}

	@Override
	public int getInPlayUpdateCommandMinecartPacketId() {
		return getInId(PacketPlayInSetCommandMinecart.class);
	}

	@Override
	public int getInPlayUpdateStructureBlockPacketId() {
		return getInId(PacketPlayInStruct.class);
	}

	@Override
	public int getInPlayJigsawUpdatePacketId() {
		return getInId(PacketPlayInSetJigsaw.class);
	}

	@Override
	public int getInPlayJigsawGenerateStructurePacketId() {
		return getInId(PacketPlayInJigsawGenerate.class);
	}

	@Override
	public int getInPlaySyncPong() {
		return getInId(ServerboundPongPacket.class);
	}

	protected static final Map<Class<? extends Packet<?>>, EnumProtocol> protocolmap = getProtocolMap();
	protected static final FieldReader<Map<EnumProtocolDirection, Object>> directionMapField = getDirectionMapField();
	protected static final FieldReader<Map<Class<? extends Packet<?>>, Integer>> packetIdMapField = getPacketIdMapField();

	@SuppressWarnings("unchecked")
	protected static final Map<Class<? extends Packet<?>>, EnumProtocol> getProtocolMap() {
		try {
			return (Map<Class<? extends Packet<?>>, EnumProtocol>) ReflectionUtils.findField(EnumProtocol.class, "h").get(null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new UncheckedReflectionException(e);
		}
	}

	protected static FieldReader<Map<EnumProtocolDirection, Object>> getDirectionMapField() {
		try {
			return FieldReader.of(EnumProtocol.class, "j", ReflectionUtils.generifyClass(Map.class));
		} catch (IllegalArgumentException e) {
			throw new UncheckedReflectionException(e);
		}
	}

	protected static FieldReader<Map<Class<? extends Packet<?>>, Integer>> getPacketIdMapField() {
		try {
			return FieldReader.of(SpigotPacketFactory.class.getClassLoader().loadClass(EnumProtocol.class.getName() + "$a"), "b", ReflectionUtils.generifyClass(Map.class));
		} catch (IllegalArgumentException | ClassNotFoundException e) {
			throw new UncheckedReflectionException(e);
		}
	}

	protected static int getPacketId(Class<?> packetClass, EnumProtocolDirection direction) {
		try {
			EnumProtocol protocol = protocolmap.get(packetClass);
			Object registry = directionMapField.get(protocol).get(direction);
			return packetIdMapField.get(registry).get(packetClass);
		} catch (Exception e) {
			throw new RuntimeException("Unable to get packet id", e);
		}
	}

	protected static final int getOutId(Class<? extends Packet<?>> packetClass) {
		return getPacketId(packetClass, EnumProtocolDirection.b);
	}

	protected static final int getInId(Class<? extends Packet<?>> packetClass) {
		return getPacketId(packetClass, EnumProtocolDirection.a);
	}

}
