package protocolsupport.zplatform.impl.glowstone;

import java.lang.reflect.Field;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldType;
import org.bukkit.entity.Entity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flowpowered.network.Codec.CodecRegistration;
import com.flowpowered.network.Message;
import com.flowpowered.network.service.CodecLookupService;

import net.glowstone.GlowServer;
import net.glowstone.entity.meta.profile.PlayerProfile;
import net.glowstone.net.message.KickMessage;
import net.glowstone.net.message.SetCompressionMessage;
import net.glowstone.net.message.handshake.HandshakeMessage;
import net.glowstone.net.message.login.EncryptionKeyRequestMessage;
import net.glowstone.net.message.login.EncryptionKeyResponseMessage;
import net.glowstone.net.message.login.LoginStartMessage;
import net.glowstone.net.message.login.LoginSuccessMessage;
import net.glowstone.net.message.play.entity.AnimateEntityMessage;
import net.glowstone.net.message.play.entity.AttachEntityMessage;
import net.glowstone.net.message.play.entity.CollectItemMessage;
import net.glowstone.net.message.play.entity.DestroyEntitiesMessage;
import net.glowstone.net.message.play.entity.EntityEffectMessage;
import net.glowstone.net.message.play.entity.EntityEquipmentMessage;
import net.glowstone.net.message.play.entity.EntityHeadRotationMessage;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import net.glowstone.net.message.play.entity.EntityPropertyMessage;
import net.glowstone.net.message.play.entity.EntityRemoveEffectMessage;
import net.glowstone.net.message.play.entity.EntityRotationMessage;
import net.glowstone.net.message.play.entity.EntityStatusMessage;
import net.glowstone.net.message.play.entity.EntityTeleportMessage;
import net.glowstone.net.message.play.entity.EntityVelocityMessage;
import net.glowstone.net.message.play.entity.RelativeEntityPositionMessage;
import net.glowstone.net.message.play.entity.RelativeEntityPositionRotationMessage;
import net.glowstone.net.message.play.entity.SetCooldownMessage;
import net.glowstone.net.message.play.entity.SetPassengerMessage;
import net.glowstone.net.message.play.entity.SpawnLightningStrikeMessage;
import net.glowstone.net.message.play.entity.SpawnMobMessage;
import net.glowstone.net.message.play.entity.SpawnObjectMessage;
import net.glowstone.net.message.play.entity.SpawnPaintingMessage;
import net.glowstone.net.message.play.entity.SpawnPlayerMessage;
import net.glowstone.net.message.play.entity.SpawnXpOrbMessage;
import net.glowstone.net.message.play.entity.VehicleMoveMessage;
import net.glowstone.net.message.play.game.BlockActionMessage;
import net.glowstone.net.message.play.game.BlockBreakAnimationMessage;
import net.glowstone.net.message.play.game.BlockChangeMessage;
import net.glowstone.net.message.play.game.ChatMessage;
import net.glowstone.net.message.play.game.ChunkDataMessage;
import net.glowstone.net.message.play.game.ClientSettingsMessage;
import net.glowstone.net.message.play.game.ExperienceMessage;
import net.glowstone.net.message.play.game.ExplosionMessage;
import net.glowstone.net.message.play.game.HealthMessage;
import net.glowstone.net.message.play.game.IncomingChatMessage;
import net.glowstone.net.message.play.game.JoinGameMessage;
import net.glowstone.net.message.play.game.MapDataMessage;
import net.glowstone.net.message.play.game.MultiBlockChangeMessage;
import net.glowstone.net.message.play.game.NamedSoundEffectMessage;
import net.glowstone.net.message.play.game.PingMessage;
import net.glowstone.net.message.play.game.PlayEffectMessage;
import net.glowstone.net.message.play.game.PlayParticleMessage;
import net.glowstone.net.message.play.game.PluginMessage;
import net.glowstone.net.message.play.game.PositionRotationMessage;
import net.glowstone.net.message.play.game.RespawnMessage;
import net.glowstone.net.message.play.game.SignEditorMessage;
import net.glowstone.net.message.play.game.SoundEffectMessage;
import net.glowstone.net.message.play.game.SpawnPositionMessage;
import net.glowstone.net.message.play.game.StateChangeMessage;
import net.glowstone.net.message.play.game.StatisticMessage;
import net.glowstone.net.message.play.game.TimeMessage;
import net.glowstone.net.message.play.game.TitleMessage;
import net.glowstone.net.message.play.game.UnloadChunkMessage;
import net.glowstone.net.message.play.game.UpdateBlockEntityMessage;
import net.glowstone.net.message.play.game.UpdateSignMessage;
import net.glowstone.net.message.play.game.UserListHeaderFooterMessage;
import net.glowstone.net.message.play.game.UserListItemMessage;
import net.glowstone.net.message.play.game.WorldBorderMessage;
import net.glowstone.net.message.play.inv.CloseWindowMessage;
import net.glowstone.net.message.play.inv.CreativeItemMessage;
import net.glowstone.net.message.play.inv.EnchantItemMessage;
import net.glowstone.net.message.play.inv.HeldItemMessage;
import net.glowstone.net.message.play.inv.OpenWindowMessage;
import net.glowstone.net.message.play.inv.SetWindowContentsMessage;
import net.glowstone.net.message.play.inv.SetWindowSlotMessage;
import net.glowstone.net.message.play.inv.TransactionMessage;
import net.glowstone.net.message.play.inv.WindowClickMessage;
import net.glowstone.net.message.play.inv.WindowPropertyMessage;
import net.glowstone.net.message.play.player.BlockPlacementMessage;
import net.glowstone.net.message.play.player.BossBarMessage;
import net.glowstone.net.message.play.player.CameraMessage;
import net.glowstone.net.message.play.player.ClientStatusMessage;
import net.glowstone.net.message.play.player.CombatEventMessage;
import net.glowstone.net.message.play.player.DiggingMessage;
import net.glowstone.net.message.play.player.InteractEntityMessage;
import net.glowstone.net.message.play.player.PlayerAbilitiesMessage;
import net.glowstone.net.message.play.player.PlayerActionMessage;
import net.glowstone.net.message.play.player.PlayerLookMessage;
import net.glowstone.net.message.play.player.PlayerPositionLookMessage;
import net.glowstone.net.message.play.player.PlayerPositionMessage;
import net.glowstone.net.message.play.player.PlayerSwingArmMessage;
import net.glowstone.net.message.play.player.PlayerUpdateMessage;
import net.glowstone.net.message.play.player.ResourcePackSendMessage;
import net.glowstone.net.message.play.player.ResourcePackStatusMessage;
import net.glowstone.net.message.play.player.ServerDifficultyMessage;
import net.glowstone.net.message.play.player.SpectateMessage;
import net.glowstone.net.message.play.player.SteerVehicleMessage;
import net.glowstone.net.message.play.player.TabCompleteMessage;
import net.glowstone.net.message.play.player.TabCompleteResponseMessage;
import net.glowstone.net.message.play.player.TeleportConfirmMessage;
import net.glowstone.net.message.play.player.UseBedMessage;
import net.glowstone.net.message.play.player.UseItemMessage;
import net.glowstone.net.message.play.scoreboard.ScoreboardDisplayMessage;
import net.glowstone.net.message.play.scoreboard.ScoreboardObjectiveMessage;
import net.glowstone.net.message.play.scoreboard.ScoreboardScoreMessage;
import net.glowstone.net.message.play.scoreboard.ScoreboardTeamMessage;
import net.glowstone.net.message.status.StatusPingMessage;
import net.glowstone.net.message.status.StatusRequestMessage;
import net.glowstone.net.message.status.StatusResponseMessage;
import net.glowstone.net.protocol.GlowProtocol;
import net.glowstone.net.protocol.ProtocolType;
import net.glowstone.util.TextMessage;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.events.ServerPingResponseEvent;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.zplatform.PlatformPacketFactory;

public class GlowStonePacketFactory implements PlatformPacketFactory {

	@Override
	public Message createInboundInventoryClosePacket() {
		return new CloseWindowMessage(0);
	}

	@Override
	public Message createInboundInventoryConfirmTransactionPacket(int windowId, int actionNumber, boolean accepted) {
		return new TransactionMessage(windowId, actionNumber, accepted);
	}
	
	@Override
	public Message createOutboundChatPacket(String message, int position) {
		return new ChatMessage(new TextMessage(message), position);
	}

	@Override
	public Message createTabHeaderFooterPacket(BaseComponent header, BaseComponent footer) {
		// TODO: Change to use BaseComponent when it will be possible
		return new UserListHeaderFooterMessage(new TextMessage(header.toLegacyText()), new TextMessage(header.toLegacyText()));
	}

	@Override
	public Message createTitleResetPacket() {
		return new TitleMessage(TitleMessage.Action.RESET);
	}

	@Override
	public Message createTitleClearPacket() {
		return new TitleMessage(TitleMessage.Action.CLEAR);
	}

	@Override
	public Message createTitleMainPacket(String title) {
		return new TitleMessage(TitleMessage.Action.TITLE, new TextMessage(title));
	}

	@Override
	public Message createTitleSubPacket(String title) {
		return new TitleMessage(TitleMessage.Action.SUBTITLE, new TextMessage(title));
	}

	@Override
	public Message createTitleParamsPacket(int fadeIn, int stay, int fadeOut) {
		return new TitleMessage(TitleMessage.Action.TIMES, fadeIn, stay, fadeOut);
	}

	@Override
	public Message createLoginDisconnectPacket(String message) {
		return new KickMessage(message);
	}

	@Override
	public Message createPlayDisconnectPacket(String message) {
		return new KickMessage(message);
	}

	@Override
	public Message createLoginEncryptionBeginPacket(PublicKey publicKey, byte[] randomBytes) {
		return new EncryptionKeyRequestMessage("", publicKey.getEncoded(), randomBytes);
	}

	@Override
	public Message createSetCompressionPacket(int threshold) {
		return new SetCompressionMessage(threshold);
	}

	@Override
	public Message createBlockBreakSoundPacket(Position pos, Material type) {
		return null; // TODO: create a getStepSound() equivalent
	}

	@Override
	public Message createStatusPongPacket(long pingId) {
		return new StatusPingMessage(pingId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Message createStausServerInfoPacket(List<String> profiles, ServerPingResponseEvent.ProtocolInfo info, String icon, String motd, int maxPlayers) {
		GlowServer server = (GlowServer) Bukkit.getServer();
		JSONObject json = new JSONObject();

		JSONObject version = new JSONObject();
		version.put("name", info.getName());
		version.put("protocol", info.getId());
		json.put("version", version);

		JSONObject players = new JSONObject();
		players.put("max", maxPlayers);
		players.put("online", profiles.size());
		if (!profiles.isEmpty()) {
			JSONArray playerSample = new JSONArray();
			UUID randomUUID = UUID.randomUUID();
			PlayerProfile[] playerProfiles = new PlayerProfile[profiles.size()];
			for (int i = 0; i < profiles.size(); i++) {
				playerProfiles[i] = new PlayerProfile(profiles.get(i), randomUUID);
			}
			playerProfiles = Arrays.copyOfRange(playerProfiles, 0, Math.min(playerProfiles.length, server.getPlayerSampleCount()));
			for (PlayerProfile profile : playerProfiles) {
				JSONObject sample = new JSONObject();
				sample.put("name", profile.getName());
				sample.put("id", profile.getUniqueId().toString());
				playerSample.add(sample);
			}
			players.put("sample", playerSample);
		}
		json.put("players", players);

		JSONObject description = new JSONObject();
		description.put("text", motd);
		json.put("description", description);

		if ((icon != null) && !icon.isEmpty()) {
			json.put("favicon", icon);
		}

		JSONArray modList = new JSONArray();
		JSONObject modinfo = new JSONObject();
		modinfo.put("type", "vanilla");
		modinfo.put("modList", modList);
		modinfo.put("clientModsAllowed", true);
		json.put("modinfo", modinfo);
		return new StatusResponseMessage(json);
	}


	@Override
	public Message createLoginSuccessPacket(GameProfile profile) {
		return new LoginSuccessMessage(profile.getUUID().toString(), profile.getName());
	}

	@Override
	public Message createEmptyCustomPayloadPacket(String tag) {
		return new PluginMessage(tag, new byte[0]);
	}

	@Override
	public Message createFakeJoinGamePacket() {
		return new JoinGameMessage(0, 0, 0, 0, 0, WorldType.NORMAL.name(), false);
	}

	@Override
	public Object createEntityStatusPacket(Entity entity, int status) {
		return new EntityStatusMessage(entity.getEntityId(), status);
	}


	@Override
	public int getOutLoginDisconnectPacketId() {
		return getOpcode(ProtocolType.LOGIN, OUTBOUND, KickMessage.class);
	}

	@Override
	public int getOutLoginEncryptionBeginPacketId() {
		return getOpcode(ProtocolType.LOGIN, OUTBOUND, EncryptionKeyRequestMessage.class);
	}

	@Override
	public int getOutLoginSuccessPacketId() {
		return getOpcode(ProtocolType.LOGIN, OUTBOUND, LoginSuccessMessage.class);
	}

	@Override
	public int getOutLoginSetCompressionPacketId() {
		return getOpcode(ProtocolType.LOGIN, OUTBOUND, SetCompressionMessage.class);
	}

	@Override
	public int getOutStatusServerInfoPacketId() {
		return getOpcode(ProtocolType.STATUS, OUTBOUND, StatusResponseMessage.class);
	}

	@Override
	public int getOutStatusPongPacketId() {
		return getOpcode(ProtocolType.STATUS, OUTBOUND, StatusPingMessage.class);
	}

	@Override
	public int getOutPlayKeepAlivePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, PingMessage.class);
	}

	@Override
	public int getOutPlayLoginPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, JoinGameMessage.class);
	}

	@Override
	public int getOutPlayChatPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, ChatMessage.class);
	}

	@Override
	public int getOutPlayUpdateTimePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, TimeMessage.class);
	}

	@Override
	public int getOutPlayEntityEquipmentPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, EntityEquipmentMessage.class);
	}

	@Override
	public int getOutPlaySpawnPositionPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, SpawnPositionMessage.class);
	}

	@Override
	public int getOutPlayUpdateHealthPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, HealthMessage.class);
	}

	@Override
	public int getOutPlayRespawnPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, RespawnMessage.class);
	}

	@Override
	public int getOutPlayPositionPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, PositionRotationMessage.class);
	}

	@Override
	public int getOutPlayHeldSlotPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, HeldItemMessage.class);
	}

	@Override
	public int getOutPlayBedPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, UseBedMessage.class);
	}

	@Override
	public int getOutPlayAnimationPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, AnimateEntityMessage.class);
	}

	@Override
	public int getOutPlaySpawnNamedPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, SpawnPlayerMessage.class);
	}

	@Override
	public int getOutPlayCollectEffectPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, CollectItemMessage.class);
	}

	@Override
	public int getOutPlaySpawnObjectPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, SpawnObjectMessage.class);
	}

	@Override
	public int getOutPlaySpawnLivingPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, SpawnMobMessage.class);
	}

	@Override
	public int getOutPlaySpawnPaintingPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, SpawnPaintingMessage.class);
	}

	@Override
	public int getOutPlaySpawnExpOrbPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, SpawnXpOrbMessage.class);
	}

	@Override
	public int getOutPlayEntityVelocityPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, EntityVelocityMessage.class);
	}

	@Override
	public int getOutPlayEntityDestroyPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, DestroyEntitiesMessage.class);
	}

	@Override
	public int getOutPlayEntityPacketId() {
		return 0x25; // TODO: not implemented in Glowstone
	}

	@Override
	public int getOutPlayEntityRelMovePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, RelativeEntityPositionMessage.class);
	}

	@Override
	public int getOutPlayEntityLookPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, EntityRotationMessage.class);
	}

	@Override
	public int getOutPlayEntityRelMoveLookPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, RelativeEntityPositionRotationMessage.class);
	}

	@Override
	public int getOutPlayEntityTeleportPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, EntityTeleportMessage.class);
	}

	@Override
	public int getOutPlayEntityHeadRotationPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, EntityHeadRotationMessage.class);
	}

	@Override
	public int getOutPlayEntityStatusPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, EntityStatusMessage.class);
	}

	@Override
	public int getOutPlayEntityLeashPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, AttachEntityMessage.class);
	}

	@Override
	public int getOutPlayEntityMetadataPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, EntityMetadataMessage.class);
	}

	@Override
	public int getOutPlayEntityEffectAddPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, EntityEffectMessage.class);
	}

	@Override
	public int getOutPlayEntityEffectRemovePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, EntityRemoveEffectMessage.class);
	}

	@Override
	public int getOutPlayExperiencePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, ExperienceMessage.class);
	}

	@Override
	public int getOutPlayEntityAttributesPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, EntityPropertyMessage.class);
	}

	@Override
	public int getOutPlayChunkSinglePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, ChunkDataMessage.class);
	}

	@Override
	public int getOutPlayBlockChangeMultiPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, MultiBlockChangeMessage.class);
	}

	@Override
	public int getOutPlayBlockChangeSinglePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, BlockChangeMessage.class);
	}

	@Override
	public int getOutPlayBlockActionPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, BlockActionMessage.class);
	}

	@Override
	public int getOutPlayBlockBreakAnimationPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, BlockBreakAnimationMessage.class);
	}

	@Override
	public int getOutPlayExplosionPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, ExplosionMessage.class);
	}

	@Override
	public int getOutPlayWorldEventPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, PlayEffectMessage.class);
	}

	@Override
	public int getOutPlayWorldSoundPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, SoundEffectMessage.class);
	}

	@Override
	public int getOutPlayWorldParticlesPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, PlayParticleMessage.class);
	}

	@Override
	public int getOutPlayGameStateChangePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, StateChangeMessage.class);
	}

	@Override
	public int getOutPlaySpawnWeatherPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, SpawnLightningStrikeMessage.class);
	}

	@Override
	public int getOutPlayWindowOpenPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, OpenWindowMessage.class);
	}

	@Override
	public int getOutPlayWindowClosePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, CloseWindowMessage.class);
	}

	@Override
	public int getOutPlayWindowSetSlotPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, SetWindowSlotMessage.class);
	}

	@Override
	public int getOutPlayWindowSetItemsPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, SetWindowContentsMessage.class);
	}

	@Override
	public int getOutPlayWindowDataPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, WindowPropertyMessage.class);
	}

	@Override
	public int getOutPlayWindowTransactionPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, TransactionMessage.class);
	}

	@Override
	public int getOutPlayMapPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, MapDataMessage.class);
	}

	@Override
	public int getOutPlayUpdateTilePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, UpdateBlockEntityMessage.class);
	}

	@Override
	public int getOutPlaySignEditorPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, SignEditorMessage.class);
	}

	@Override
	public int getOutPlayStatisticsPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, StatisticMessage.class);
	}

	@Override
	public int getOutPlayPlayerInfoPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, UserListItemMessage.class);
	}

	@Override
	public int getOutPlayAbilitiesPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, PlayerAbilitiesMessage.class);
	}

	@Override
	public int getOutPlayTabCompletePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, TabCompleteResponseMessage.class);
	}

	@Override
	public int getOutPlayScoreboardObjectivePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, ScoreboardObjectiveMessage.class);
	}

	@Override
	public int getOutPlayScoreboardScorePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, ScoreboardScoreMessage.class);
	}

	@Override
	public int getOutPlayScoreboardDisplaySlotPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, ScoreboardDisplayMessage.class);
	}

	@Override
	public int getOutPlayScoreboardTeamPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, ScoreboardTeamMessage.class);
	}

	@Override
	public int getOutPlayCustomPayloadPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, PluginMessage.class);
	}

	@Override
	public int getOutPlayKickDisconnectPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, KickMessage.class);
	}

	@Override
	public int getOutPlayResourcePackPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, ResourcePackSendMessage.class);
	}

	@Override
	public int getOutPlayCameraPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, CameraMessage.class);
	}

	@Override
	public int getOutPlayWorldBorderPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, WorldBorderMessage.class);
	}

	@Override
	public int getOutPlayTitlePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, TitleMessage.class);
	}

	@Override
	public int getOutPlayPlayerListHeaderFooterPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, UserListHeaderFooterMessage.class);
	}

	@Override
	public int getOutPlaySetPassengersPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, SetPassengerMessage.class);
	}

	@Override
	public int getOutPlayChunkUnloadPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, UnloadChunkMessage.class);
	}

	@Override
	public int getOutPlayWorldCustomSoundPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, NamedSoundEffectMessage.class);
	}

	@Override
	public int getOutPlayServerDifficultyPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, ServerDifficultyMessage.class);
	}

	@Override
	public int getOutPlayCombatEventPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, CombatEventMessage.class);
	}

	@Override
	public int getOutPlayBossBarPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, BossBarMessage.class);
	}

	@Override
	public int getOutPlaySetCooldownPacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, SetCooldownMessage.class);
	}

	@Override
	public int getOutPlayVehicleMovePacketId() {
		return getOpcode(ProtocolType.PLAY, OUTBOUND, VehicleMoveMessage.class);
	}

	@Override
	public int getOutPlayUnlockRecipesPacketId() {
		return 0x31; // TODO: not implemented in Glowstone
	}

	@Override
	public int getOutPlayAdvancementsTabPacketId() {
		return 0x37; // TODO: not implemented in Glowstone
	}


	@Override
	public int getOutPlayAdvancementsPacketId() {
		return 0x4D; // TODO: not implemented in Glowstone
	}

	@Override
	public int getOutPlayCraftingGridConfirmPacketId() {
		return 0x2B; // TODO: not implemented in Glowstone
	}


	@Override
	public int getInHandshakeStartPacketId() {
		return getOpcode(ProtocolType.HANDSHAKE, INBOUND, HandshakeMessage.class);
	}

	@Override
	public int getInStatusRequestPacketId() {
		return getOpcode(ProtocolType.STATUS, INBOUND, StatusRequestMessage.class);
	}

	@Override
	public int getInStatusPingPacketId() {
		return getOpcode(ProtocolType.STATUS, INBOUND, StatusPingMessage.class);
	}

	@Override
	public int getInLoginStartPacketId() {
		return getOpcode(ProtocolType.LOGIN, INBOUND, LoginStartMessage.class);
	}

	@Override
	public int getInLoginEncryptionBeginPacketId() {
		return getOpcode(ProtocolType.LOGIN, INBOUND, EncryptionKeyResponseMessage.class);
	}

	@Override
	public int getInPlayKeepAlivePacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, PingMessage.class);
	}

	@Override
	public int getInPlayChatPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, IncomingChatMessage.class);
	}

	@Override
	public int getInPlayUseEntityPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, InteractEntityMessage.class);
	}

	@Override
	public int getInPlayPlayerPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, PlayerUpdateMessage.class);
	}

	@Override
	public int getInPlayPositionPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, PlayerPositionMessage.class);
	}

	@Override
	public int getInPlayLookPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, PlayerLookMessage.class);
	}

	@Override
	public int getInPlayPositionLookPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, PlayerPositionLookMessage.class);
	}

	@Override
	public int getInPlayBlockDigPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, DiggingMessage.class);
	}

	@Override
	public int getInPlayBlockPlacePacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, UseItemMessage.class);
	}

	@Override
	public int getInPlayHeldSlotPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, HeldItemMessage.class);
	}

	@Override
	public int getInPlayAnimationPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, PlayerSwingArmMessage.class);
	}

	@Override
	public int getInPlayEntityActionPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, PlayerActionMessage.class);
	}

	@Override
	public int getInPlayMoveVehiclePacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, VehicleMoveMessage.class);
	}

	@Override
	public int getInPlaySteerBoatPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, SteerVehicleMessage.class);
	}

	@Override
	public int getInPlaySteerVehiclePacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, SteerVehicleMessage.class);
	}

	@Override
	public int getInPlayWindowClosePacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, CloseWindowMessage.class);
	}

	@Override
	public int getInPlayWindowClickPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, WindowClickMessage.class);
	}

	@Override
	public int getInPlayWindowTransactionPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, TransactionMessage.class);
	}

	@Override
	public int getInPlayCreativeSetSlotPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, CreativeItemMessage.class);
	}

	@Override
	public int getInPlayEnchantSelectPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, EnchantItemMessage.class);
	}

	@Override
	public int getInPlayUpdateSignPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, UpdateSignMessage.class);
	}

	@Override
	public int getInPlayAbilitiesPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, PlayerAbilitiesMessage.class);
	}

	@Override
	public int getInPlayTabCompletePacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, TabCompleteMessage.class);
	}

	@Override
	public int getInPlaySettingsPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, ClientSettingsMessage.class);
	}

	@Override
	public int getInPlayClientCommandPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, ClientStatusMessage.class);
	}

	@Override
	public int getInPlayCustomPayloadPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, PluginMessage.class);
	}

	@Override
	public int getInPlayUseItemPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, BlockPlacementMessage.class);
	}

	@Override
	public int getInPlaySpectatePacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, SpectateMessage.class);
	}

	@Override
	public int getInPlayResourcePackStatusPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, ResourcePackStatusMessage.class);
	}

	@Override
	public int getInPlayTeleportAcceptPacketId() {
		return getOpcode(ProtocolType.PLAY, INBOUND, TeleportConfirmMessage.class);
	}

	@Override
	public int getInPlayCraftingBookPacketId() {
		return 0x17;  // TODO: not implemented in Glowstone
	}

	@Override
	public int getInPlayPrepareCraftingGridPacketId() {
		return 0x01; // TODO: not implemented in Glowstone
	}

	@Override
	public int getInPlayAdvancementTabPacketId() {
		return 0x19; // TODO: not implemented in Glowstone
	}


	private static final int INBOUND = 0, OUTBOUND = 1;

	@SuppressWarnings("unchecked")
	private static int getOpcode(ProtocolType protocolType, int direction, Class<? extends Message> messageClass) {
		try {
			GlowProtocol protocol = protocolType.getProtocol();
			Class<? extends GlowProtocol> protocolClass = protocol.getClass();
			Field lookupServiceField = ReflectionUtils.getField(protocolClass, direction == INBOUND ? "inboundCodecs" : "outboundCodecs");
			lookupServiceField.setAccessible(true);
			CodecLookupService service = (CodecLookupService) lookupServiceField.get(protocol);
			Field messagesField = ReflectionUtils.getField(service.getClass(), "messages");
			messagesField.setAccessible(true);
			ConcurrentMap<Class<? extends Message>, CodecRegistration> messageMap = (ConcurrentMap<Class<? extends Message>, CodecRegistration>) messagesField.get(service);
			return messageMap.get(messageClass).getOpcode();
		} catch (Throwable ignored) {
			throw new RuntimeException("Unable to get opcode");
		}
	}

}
