package protocolsupport.zplatform.impl.spigot;

import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.spigotmc.SneakyThrow;
import org.spigotmc.SpigotConfig;

import com.google.common.collect.BiMap;
import com.mojang.authlib.GameProfile;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_11_R1.Block;
import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.EnumDifficulty;
import net.minecraft.server.v1_11_R1.EnumGamemode;
import net.minecraft.server.v1_11_R1.EnumProtocol;
import net.minecraft.server.v1_11_R1.EnumProtocolDirection;
import net.minecraft.server.v1_11_R1.Packet;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook;
import net.minecraft.server.v1_11_R1.PacketDataSerializer;
import net.minecraft.server.v1_11_R1.PacketLoginOutDisconnect;
import net.minecraft.server.v1_11_R1.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_11_R1.PacketLoginOutSetCompression;
import net.minecraft.server.v1_11_R1.PacketLoginOutSuccess;
import net.minecraft.server.v1_11_R1.PacketPlayInCloseWindow;
import net.minecraft.server.v1_11_R1.PacketPlayOutAbilities;
import net.minecraft.server.v1_11_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_11_R1.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_11_R1.PacketPlayOutBed;
import net.minecraft.server.v1_11_R1.PacketPlayOutBlockAction;
import net.minecraft.server.v1_11_R1.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_11_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_11_R1.PacketPlayOutBoss;
import net.minecraft.server.v1_11_R1.PacketPlayOutCamera;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import net.minecraft.server.v1_11_R1.PacketPlayOutCloseWindow;
import net.minecraft.server.v1_11_R1.PacketPlayOutCollect;
import net.minecraft.server.v1_11_R1.PacketPlayOutCombatEvent;
import net.minecraft.server.v1_11_R1.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_11_R1.PacketPlayOutCustomSoundEffect;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntity;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_11_R1.PacketPlayOutExperience;
import net.minecraft.server.v1_11_R1.PacketPlayOutExplosion;
import net.minecraft.server.v1_11_R1.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_11_R1.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_11_R1.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_11_R1.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_11_R1.PacketPlayOutLogin;
import net.minecraft.server.v1_11_R1.PacketPlayOutMap;
import net.minecraft.server.v1_11_R1.PacketPlayOutMapChunk;
import net.minecraft.server.v1_11_R1.PacketPlayOutMount;
import net.minecraft.server.v1_11_R1.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_11_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_11_R1.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_11_R1.PacketPlayOutOpenSignEditor;
import net.minecraft.server.v1_11_R1.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_11_R1.PacketPlayOutPosition;
import net.minecraft.server.v1_11_R1.PacketPlayOutRemoveEntityEffect;
import net.minecraft.server.v1_11_R1.PacketPlayOutResourcePackSend;
import net.minecraft.server.v1_11_R1.PacketPlayOutRespawn;
import net.minecraft.server.v1_11_R1.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_11_R1.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_11_R1.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_11_R1.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_11_R1.PacketPlayOutServerDifficulty;
import net.minecraft.server.v1_11_R1.PacketPlayOutSetCooldown;
import net.minecraft.server.v1_11_R1.PacketPlayOutSetSlot;
import net.minecraft.server.v1_11_R1.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_11_R1.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.v1_11_R1.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_11_R1.PacketPlayOutSpawnEntityPainting;
import net.minecraft.server.v1_11_R1.PacketPlayOutSpawnEntityWeather;
import net.minecraft.server.v1_11_R1.PacketPlayOutSpawnPosition;
import net.minecraft.server.v1_11_R1.PacketPlayOutStatistic;
import net.minecraft.server.v1_11_R1.PacketPlayOutTabComplete;
import net.minecraft.server.v1_11_R1.PacketPlayOutTileEntityData;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTransaction;
import net.minecraft.server.v1_11_R1.PacketPlayOutUnloadChunk;
import net.minecraft.server.v1_11_R1.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_11_R1.PacketPlayOutUpdateHealth;
import net.minecraft.server.v1_11_R1.PacketPlayOutUpdateTime;
import net.minecraft.server.v1_11_R1.PacketPlayOutVehicleMove;
import net.minecraft.server.v1_11_R1.PacketPlayOutWindowData;
import net.minecraft.server.v1_11_R1.PacketPlayOutWindowItems;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_11_R1.PacketStatusOutPong;
import net.minecraft.server.v1_11_R1.PacketStatusOutServerInfo;
import net.minecraft.server.v1_11_R1.ServerPing;
import net.minecraft.server.v1_11_R1.ServerPing.ServerData;
import net.minecraft.server.v1_11_R1.ServerPing.ServerPingPlayerSample;
import net.minecraft.server.v1_11_R1.SoundCategory;
import net.minecraft.server.v1_11_R1.SoundEffectType;
import net.minecraft.server.v1_11_R1.WorldType;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.events.ServerPingResponseEvent.ProtocolInfo;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.zplatform.PlatformPacketFactory;

public class SpigotPacketFactory implements PlatformPacketFactory {

	public Object createInboundInventoryClosePacket() {
		return new PacketPlayInCloseWindow();
	}

	public Object createOutboundChatPacket(String message, int position) {
		return new PacketPlayOutChat(ChatSerializer.a(message), (byte) position);
	}

	private static final BaseComponent empty = new TextComponent("");

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

	public Object createTitleResetPacket() {
		return new PacketPlayOutTitle(EnumTitleAction.CLEAR, null);
	}

	public Object createTitleClearPacket() {
		return new PacketPlayOutTitle(EnumTitleAction.RESET, null);
	}

	public Object createTitleMainPacket(String title) {
		return new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a(title));
	}

	public Object createTitleSubPacket(String title) {
		return new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a(title));
	}

	public Object createTitleParamsPacket(int fadeIn, int stay, int fadeOut) {
		return new PacketPlayOutTitle(fadeIn, stay, fadeOut);
	}

	public Object createLoginDisconnectPacket(String message) {
		return new PacketLoginOutDisconnect(new ChatComponentText(message));
	}

	public Object createPlayDisconnectPacket(String message) {
		return new PacketPlayOutKickDisconnect(new ChatComponentText(message));
	}

	public Object createLoginEncryptionBeginPacket(PublicKey publicKey, byte[] randomBytes) {
		return new PacketLoginOutEncryptionBegin("", publicKey, randomBytes);
	}

	public Object createSetCompressionPacket(int threshold) {
		return new PacketLoginOutSetCompression(threshold);
	}

	@SuppressWarnings("deprecation")
	public Object createBlockBreakSoundPacket(Position pos, Material type) {
		SoundEffectType blocksound = Block.getById(type.getId()).getStepSound();
		return new PacketPlayOutNamedSoundEffect(
			blocksound.e(), SoundCategory.BLOCKS,
			pos.getX(), pos.getY(), pos.getZ(),
			(blocksound.a() + 1.0F) / 2.0F,
			blocksound.b() * 0.8F
		);
	}

	public Object createStatusPongPacket(long pingId) {
		return new PacketStatusOutPong(pingId);
	}

	private final UUID profileUUID = UUID.randomUUID();
	public Object createStausServerInfoPacket(List<String> profiles, ProtocolInfo info, String icon, String motd, int maxPlayers) {
		ServerPingPlayerSample playerSample = new ServerPingPlayerSample(maxPlayers, profiles.size());

		Collections.shuffle(profiles);
		GameProfile[] gprofiles = new GameProfile[profiles.size()];
		for (int i = 0; i < profiles.size(); i++) {
			gprofiles[i] = new GameProfile(profileUUID, profiles.get(i));
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

	private static final PacketDataSerializer emptyPDS = new PacketDataSerializer(Unpooled.EMPTY_BUFFER);
	@Override
	public Object createEmptyCustomPayloadPacket(String tag) {
		return new PacketPlayOutCustomPayload(tag, emptyPDS);
	}

	@Override
	public Object createFakeJoinGamePacket() {
		return new PacketPlayOutLogin(0, EnumGamemode.NOT_SET, false, 0, EnumDifficulty.EASY, 60, WorldType.NORMAL, false);
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
	public int getOutPlayBedPacketId() {
		return getOutId(PacketPlayOutBed.class);
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

	@SuppressWarnings("unchecked")
	private static final int getOutId(Class<?> packetClass) {
		Map<Class<? extends Packet<?>>, EnumProtocol> protocolMap = null;
		try {
			protocolMap = (Map<Class<? extends Packet<?>>, EnumProtocol>) ReflectionUtils.setAccessible(EnumProtocol.class.getDeclaredField("f")).get(null);
		} catch (Throwable t) {
			SneakyThrow.sneaky(t);
		}
		EnumProtocol protocol = protocolMap.get(packetClass);
		Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>> idMap = null;
		try {
			idMap = (Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>>) ReflectionUtils.setAccessible(EnumProtocol.class.getDeclaredField("h")).get(protocol);
		} catch (Throwable t) {
			SneakyThrow.sneaky(t);
		}
		return idMap.get(EnumProtocolDirection.CLIENTBOUND).inverse().get(packetClass);
	}

}
