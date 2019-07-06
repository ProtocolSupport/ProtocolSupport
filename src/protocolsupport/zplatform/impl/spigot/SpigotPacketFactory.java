package protocolsupport.zplatform.impl.spigot;

import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_14_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.spigotmc.SpigotConfig;

import com.google.common.collect.BiMap;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_14_R1.*;
import net.minecraft.server.v1_14_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_14_R1.PacketPlayInFlying.PacketPlayInLook;
import net.minecraft.server.v1_14_R1.PacketPlayInFlying.PacketPlayInPosition;
import net.minecraft.server.v1_14_R1.PacketPlayInFlying.PacketPlayInPositionLook;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_14_R1.ServerPing.ServerData;
import net.minecraft.server.v1_14_R1.ServerPing.ServerPingPlayerSample;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.events.ServerPingResponseEvent.ProtocolInfo;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.zplatform.PlatformPacketFactory;

public class SpigotPacketFactory implements PlatformPacketFactory {

	@Override
	public Object createInboundKeepAlivePacket(long keepAliveId) {
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer());
		serializer.writeLong(keepAliveId);
		PacketPlayInKeepAlive packet = new PacketPlayInKeepAlive();
		try {
			packet.a(serializer);
		} catch (IOException e) {
		}
		return packet;
	}

	@Override
	public Object createInboundInventoryClosePacket() {
		return new PacketPlayInCloseWindow();
	}

	@Override
	public Object createInboundInventoryConfirmTransactionPacket(int windowId, int actionNumber, boolean accepted) {
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer());
		serializer.writeByte(windowId);
		serializer.writeShort(actionNumber);
		serializer.writeByte(accepted ? 1 : 0);
		PacketPlayInTransaction packet = new PacketPlayInTransaction();
		try {
			packet.a(serializer);
		} catch (IOException e) {
		}
		return packet;
	}

	@Override
	public Object createInboundPluginMessagePacket(String tag, byte[] data) {
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer());
		StringSerializer.writeString(serializer, ProtocolVersionsHelper.LATEST_PC, tag);
		serializer.writeBytes(data);
		PacketPlayInCustomPayload packet = new PacketPlayInCustomPayload();
		try {
			packet.a(serializer);
		} catch (IOException e) {
		}
		return packet;
	}

	@Override
	public Object createInboundCustomPayloadPacket(String tag, byte[] data) {
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer());
		StringSerializer.writeString(serializer, ProtocolVersionsHelper.LATEST_PC, tag);
		serializer.writeBytes(data);
		PacketPlayInCustomPayload packet = new PacketPlayInCustomPayload();
		try {
			packet.a(serializer);
		} catch (IOException e) {
		}
		return packet;
	}

	@Override
	public Object createOutboundUpdateChunkPacket(Chunk chunk) {
		return new PacketPlayOutMapChunk(((CraftChunk) chunk).getHandle(), 0xFFFF);
	}

	@Override
	public Object createOutboundChatPacket(String message, int position) {
		return new PacketPlayOutChat(ChatSerializer.a(message), ChatMessageType.a((byte) position));
	}

	protected static final BaseComponent empty = new TextComponent("");

	@Override
	public Object createTabHeaderFooterPacket(BaseComponent header, BaseComponent footer) {
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer());
		serializer.a(ChatAPI.toJSON(header != null ? header : empty));
		serializer.a(ChatAPI.toJSON(footer != null ? footer : empty));
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		try {
			packet.a(serializer);
		} catch (IOException e) {
		}
		return packet;
	}

	@Override
	public Object createTitleResetPacket() {
		return new PacketPlayOutTitle(EnumTitleAction.CLEAR, null);
	}

	@Override
	public Object createTitleClearPacket() {
		return new PacketPlayOutTitle(EnumTitleAction.RESET, null);
	}

	@Override
	public Object createTitleMainPacket(String title) {
		return new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a(title));
	}

	@Override
	public Object createTitleSubPacket(String title) {
		return new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a(title));
	}

	@Override
	public Object createTitleParamsPacket(int fadeIn, int stay, int fadeOut) {
		return new PacketPlayOutTitle(fadeIn, stay, fadeOut);
	}

	@Override
	public Object createLoginDisconnectPacket(String message) {
		return new PacketLoginOutDisconnect(new ChatComponentText(message));
	}

	@Override
	public Object createPlayDisconnectPacket(String message) {
		return new PacketPlayOutKickDisconnect(new ChatComponentText(message));
	}

	@Override
	public Object createLoginEncryptionBeginPacket(PublicKey publicKey, byte[] randomBytes) {
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
	public Object createStausServerInfoPacket(List<String> profiles, ProtocolInfo info, String icon, String motd, int maxPlayers) {
		ServerPingPlayerSample playerSample = new ServerPingPlayerSample(maxPlayers, profiles.size());

		Collections.shuffle(profiles);
		com.mojang.authlib.GameProfile[] gprofiles = new com.mojang.authlib.GameProfile[profiles.size()];
		for (int i = 0; i < profiles.size(); i++) {
			gprofiles[i] = new com.mojang.authlib.GameProfile(profileUUID, profiles.get(i));
		}
		gprofiles = Arrays.copyOfRange(gprofiles, 0, Math.min(gprofiles.length, SpigotConfig.playerSample));
		playerSample.a(gprofiles);

		ServerPing serverping = new ServerPing();
		serverping.setFavicon(icon);
		serverping.setMOTD(new ChatComponentText(motd));
		serverping.setPlayerSample(playerSample);
		serverping.setServerInfo(new ServerData(info.getName(), info.getId()));

		return new PacketStatusOutServerInfo(serverping);
	}

	@Override
	public Object createLoginSuccessPacket(GameProfile profile) {
		return new PacketLoginOutSuccess(SpigotMiscUtils.toMojangGameProfile(profile));
	}

	protected static final PacketDataSerializer emptyPDS = new PacketDataSerializer(Unpooled.EMPTY_BUFFER);
	@Override
	public Object createEmptyCustomPayloadPacket(String tag) {
		return new PacketPlayOutCustomPayload(new MinecraftKey("ps", tag), emptyPDS);
	}

	@Override
	public Object createOutboundPluginMessagePacket(String tag, ByteBuf data) {
		return new PacketPlayOutCustomPayload(new MinecraftKey("ps", tag), new PacketDataSerializer(data));
	}

	@Override
	public Object createFakeJoinGamePacket() {
		return new PacketPlayOutLogin(0, EnumGamemode.SURVIVAL, false, DimensionManager.OVERWORLD, 60, WorldType.NORMAL, 4, false);
	}

	@Override
	public Object createEntityStatusPacket(org.bukkit.entity.Entity entity, int status) {
		return new PacketPlayOutEntityStatus(((CraftEntity) entity).getHandle(), (byte) status);
	}

	@Override
	public Object createUpdateChunkPacket(Chunk chunk) {
		return new PacketPlayOutMapChunk(((CraftChunk) chunk).getHandle(), 0xFFFF);
	}

	@Override
	public Object createBlockUpdatePacket(protocolsupport.protocol.types.Position pos, int block) {
		PacketDataSerializer serializer = new PacketDataSerializer(Unpooled.buffer());
		PositionSerializer.writePosition(serializer, pos);
		VarNumberSerializer.writeVarInt(serializer, block);
		PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange();
		try {
			packet.a(serializer);
		} catch (IOException e) {
		}
		return packet;
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
	public int getOutPlayEntityPacketId() {
		return getOutId(PacketPlayOutEntity.class);
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
	public int getOutPlayChunkSinglePacketId() {
		return getOutId(PacketPlayOutMapChunk.class);
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
	public int getOutPlaySpawnWeatherPacketId() {
		return getOutId(PacketPlayOutSpawnEntityWeather.class);
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
	public int getOutPlayWindowTransactionPacketId() {
		return getOutId(PacketPlayOutTransaction.class);
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
	public int getOutPlayWorldBorderPacketId() {
		return getOutId(PacketPlayOutWorldBorder.class);
	}

	@Override
	public int getOutPlayTitlePacketId() {
		return getOutId(PacketPlayOutTitle.class);
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
	public int getOutPlayCombatEventPacketId() {
		return getOutId(PacketPlayOutCombatEvent.class);
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
	public int getOutPlayBookOpenPacketId() {
		return getOutId(PacketPlayOutOpenBook.class);
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
	public int getInPlayPlayerPacketId() {
		return getInId(PacketPlayInFlying.class);
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
	public int getInPlayWindowTransactionPacketId() {
		return getInId(PacketPlayInTransaction.class);
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
	public int getInPlayRecipeBookDataPacketId() {
		return getInId(PacketPlayInRecipeDisplayed.class);
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


	@SuppressWarnings("unchecked")
	protected static Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>> getPacketIdMap(Class<?> packetClass) {
		Map<Class<? extends Packet<?>>, EnumProtocol> protocolMap = null;
		try {
			protocolMap = (Map<Class<? extends Packet<?>>, EnumProtocol>) ReflectionUtils.setAccessible(EnumProtocol.class.getDeclaredField("f")).get(null);
		} catch (Throwable t) {
			throw new RuntimeException("Unable to get packet id map", t);
		}
		EnumProtocol protocol = protocolMap.get(packetClass);
		try {
			return (Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>>) ReflectionUtils.setAccessible(EnumProtocol.class.getDeclaredField("h")).get(protocol);
		} catch (Throwable t) {
			throw new RuntimeException("Unable to get packet id map", t);
		}
	}

	protected static final int getOutId(Class<?> packetClass) {
		return getPacketIdMap(packetClass).get(EnumProtocolDirection.CLIENTBOUND).inverse().get(packetClass);
	}

	protected static final int getInId(Class<?> packetClass) {
		return getPacketIdMap(packetClass).get(EnumProtocolDirection.SERVERBOUND).inverse().get(packetClass);
	}

}
