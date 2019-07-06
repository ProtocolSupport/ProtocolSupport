package protocolsupport.protocol.pipeline.version.v_1_8;

import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.LoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.LoginSuccess;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_8_9r1_9r2_10_11_12r1_12r2_13_14.EncryptionRequest;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_8_9r1_9r2_10_11_12r1_12r2_13_14.SetCompression;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopAdvancements;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopAdvanementsTab;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopBossBar;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopCraftRecipeConfirm;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopDeclareCommands;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopDeclareRecipes;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopDeclareTags;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopEntitySound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopLookAt;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopSetCooldown;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopSetViewCenter;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopStatistics;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopStopSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopUnlockRecipes;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopUpdateViewDistance;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopVehicleMove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.WorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.WorldSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.EntityStatus;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.InventoryConfirmTransaction;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.InventoryData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.InventorySetItems;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.InventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.ScoreboardDisplay;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.TimeUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8.EntityLeash;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8.VehiclePassengers;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8.SpawnExpOrb;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8.SpawnGlobal;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8.SpawnLiving;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2.TabComplete;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13.ChangeDimension;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.EntityAnimation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.Explosion;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.GameStateChange;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.KickDisconnect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.BookOpen;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.Chunk;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.ChunkLight;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.ChunkUnload;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.EntityEquipment;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.EntityMetadata;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.EntityRelMove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.EntityRelMoveLook;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.EntityTeleport;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.ScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.SetPosition;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.SpawnNamed;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.SpawnObject;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.SpawnPainting;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.UpdateMap;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2.EntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10.CollectEffect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10.Title;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.BlockAction;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.BlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.BlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.InventoryHorseOpen;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.InventoryOpen;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.MerchantTradeList;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.ScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.WorldParticle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockBreakAnimation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockOpenSignEditor;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.ServerDifficulty;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.SpawnPosition;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.StartGame;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.WorldEvent;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.Camera;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.Chat;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.CombatEvent;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.Entity;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.EntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.EntityEffectRemove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.EntityHeadRotation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.EntityLook;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.EntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.EntityVelocity;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.PlayerListHeaderFooter;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.PlayerListSetEntry;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.ResourcePack;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.ScoreboardScore;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.SetExperience;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.SetHealth;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14.WorldBorder;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.Pong;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14.ServerInfo;
import protocolsupport.protocol.pipeline.version.util.encoder.AbstractModernPacketEncoder;
import protocolsupport.protocol.typeremapper.packet.ChunkSendIntervalPacketQueue;
import protocolsupport.protocol.utils.registry.PacketIdTransformerRegistry;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.zplatform.ServerPlatform;

public class PacketEncoder extends AbstractModernPacketEncoder {

	protected static final PacketIdTransformerRegistry packetIdRegistry = new PacketIdTransformerRegistry();
	static {
		packetIdRegistry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_DISCONNECT_ID, 0x00);
		packetIdRegistry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_ENCRYPTION_BEGIN_ID, 0x01);
		packetIdRegistry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_SUCCESS_ID, 0x02);
		packetIdRegistry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_SET_COMPRESSION_ID, 0x03);
		packetIdRegistry.register(NetworkState.STATUS, ClientBoundPacket.STATUS_SERVER_INFO_ID, 0x00);
		packetIdRegistry.register(NetworkState.STATUS, ClientBoundPacket.STATUS_PONG_ID, 0x01);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_KEEP_ALIVE_ID, 0x00);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_LOGIN_ID, 0x01);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHAT_ID, 0x02);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_TIME_ID, 0x03);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID, 0x04);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_POSITION_ID, 0x05);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, 0x06);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_RESPAWN_ID, 0x07);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_POSITION_ID, 0x08);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_HELD_SLOT_ID, 0x09);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.LEGACY_PLAY_USE_BED_ID, 0x0A);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ANIMATION_ID, 0x0B);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_NAMED_ID, 0x0C);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_COLLECT_EFFECT_ID, 0x0D);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, 0x0E);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_LIVING_ID, 0x0F);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_PAINTING_ID, 0x10);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_EXP_ORB_ID, 0x11);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_VELOCITY_ID, 0x12);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, 0x13);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_ID, 0x14);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID, 0x15);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_LOOK_ID, 0x16);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_LOOK_ID, 0x17);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, 0x18);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_HEAD_ROTATION_ID, 0x19);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_STATUS_ID, 0x1A);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_LEASH_ID, 0x1B);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_METADATA_ID, 0x1C);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, 0x1D);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_REMOVE_ID, 0x1E);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_EXPERIENCE_ID, 0x1F);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, 0x20);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, 0x21);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, 0x22);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, 0x23);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_ACTION_ID, 0x24);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_BREAK_ANIMATION_ID, 0x25);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_EXPLOSION_ID, 0x27);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_EVENT_ID, 0x28);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_CUSTOM_SOUND_ID, 0x29);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_PARTICLES_ID, 0x2A);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_GAME_STATE_CHANGE_ID, 0x2B);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_WEATHER_ID, 0x2C);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_OPEN_ID, 0x2D);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_CLOSE_ID, 0x2E);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, 0x2F);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, 0x30);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_DATA_ID, 0x31);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_TRANSACTION_ID, 0x32);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.LEGACY_PLAY_UPDATE_SIGN_ID, 0x33);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_MAP_ID, 0x34);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_TILE_ID, 0x35);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SIGN_EDITOR_ID, 0x36);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_STATISTICS_ID, 0x37);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_PLAYER_INFO_ID, 0x38);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ABILITIES_ID, 0x39);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_TAB_COMPLETE_ID, 0x3A);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_OBJECTIVE_ID, 0x3B);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_SCORE_ID, 0x3C);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_DISPLAY_SLOT_ID, 0x3D);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID, 0x3E);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, 0x3F);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_KICK_DISCONNECT_ID, 0x40);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SERVER_DIFFICULTY_ID, 0x41);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_COMBAT_EVENT_ID, 0x42);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CAMERA_ID, 0x43);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_BORDER_ID, 0x44);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_TITLE_ID, 0x45);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_PLAYER_LIST_HEADER_FOOTER_ID, 0x47);
		packetIdRegistry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_RESOURCE_PACK_ID, 0x48);
	}

	@Override
	protected int getNewPacketId(NetworkState currentProtocol, int oldPacketId) {
		return packetIdRegistry.getNewPacketId(currentProtocol, oldPacketId);
	}

	{
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_SUCCESS_ID, LoginSuccess::new);
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_ENCRYPTION_BEGIN_ID, EncryptionRequest::new);
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_DISCONNECT_ID, LoginDisconnect::new);
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_SET_COMPRESSION_ID, SetCompression::new);
		registry.register(NetworkState.STATUS, ClientBoundPacket.STATUS_SERVER_INFO_ID, ServerInfo::new);
		registry.register(NetworkState.STATUS, ClientBoundPacket.STATUS_PONG_ID, Pong::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_KEEP_ALIVE_ID, KeepAlive::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_LOGIN_ID, StartGame::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHAT_ID, Chat::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_TIME_ID, TimeUpdate::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID, EntityEquipment::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_POSITION_ID, SpawnPosition::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, SetHealth::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_RESPAWN_ID, ChangeDimension::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_POSITION_ID, SetPosition::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_HELD_SLOT_ID, HeldSlot::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ANIMATION_ID, EntityAnimation::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_NAMED_ID, SpawnNamed::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_COLLECT_EFFECT_ID, CollectEffect::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, SpawnObject::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_LIVING_ID, SpawnLiving::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_PAINTING_ID, SpawnPainting::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_EXP_ORB_ID, SpawnExpOrb::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_VELOCITY_ID, EntityVelocity::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, EntityDestroy::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_ID, Entity::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID, EntityRelMove::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_LOOK_ID, EntityLook::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_LOOK_ID, EntityRelMoveLook::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, EntityTeleport::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_HEAD_ROTATION_ID, EntityHeadRotation::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_STATUS_ID, EntityStatus::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_LEASH_ID, EntityLeash::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_METADATA_ID, EntityMetadata::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, EntityEffectAdd::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_REMOVE_ID, EntityEffectRemove::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_EXPERIENCE_ID, SetExperience::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, EntitySetAttributes::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, Chunk::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, BlockChangeMulti::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, BlockChangeSingle::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_ACTION_ID, BlockAction::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_BREAK_ANIMATION_ID, BlockBreakAnimation::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_EXPLOSION_ID, Explosion::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_EVENT_ID, WorldEvent::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_SOUND_ID, WorldSound::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_CUSTOM_SOUND_ID, WorldCustomSound::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_PARTICLES_ID, WorldParticle::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_GAME_STATE_CHANGE_ID, GameStateChange::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_WEATHER_ID, SpawnGlobal::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_OPEN_ID, InventoryOpen::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_HORSE_OPEN_ID, InventoryHorseOpen::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_CLOSE_ID, InventoryClose::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, InventorySetSlot::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, InventorySetItems::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_DATA_ID, InventoryData::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_TRANSACTION_ID, InventoryConfirmTransaction::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_MAP_ID, UpdateMap::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_TILE_ID, BlockTileUpdate::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SIGN_EDITOR_ID, BlockOpenSignEditor::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_PLAYER_INFO_ID, PlayerListSetEntry::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ABILITIES_ID, PlayerAbilities::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_TAB_COMPLETE_ID, TabComplete::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_OBJECTIVE_ID, ScoreboardObjective::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_SCORE_ID, ScoreboardScore::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_DISPLAY_SLOT_ID, ScoreboardDisplay::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID, ScoreboardTeam::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, CustomPayload::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_RESOURCE_PACK_ID, ResourcePack::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_KICK_DISCONNECT_ID, KickDisconnect::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CAMERA_ID, Camera::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_PLAYER_LIST_HEADER_FOOTER_ID, PlayerListHeaderFooter::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SET_PASSENGERS_ID, VehiclePassengers::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_TITLE_ID, Title::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_BORDER_ID, WorldBorder::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHUNK_UNLOAD_ID, ChunkUnload::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SERVER_DIFFICULTY_ID, ServerDifficulty::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_COMBAT_EVENT_ID, CombatEvent::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHUNK_LIGHT, ChunkLight::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_MERCHANT_TRADE_LIST, MerchantTradeList::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BOOK_OPEN, BookOpen::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SET_COOLDOWN_ID, NoopSetCooldown::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BOSS_BAR_ID, NoopBossBar::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_VEHICLE_MOVE_ID, NoopVehicleMove::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UNLOCK_RECIPES, NoopUnlockRecipes::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ADVANCEMENTS, NoopAdvancements::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ADVANCEMENTS_TAB, NoopAdvanementsTab::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CRAFT_RECIPE_CONFIRM, NoopCraftRecipeConfirm::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_STATISTICS_ID, NoopStatistics::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_DECLARE_COMMANDS, NoopDeclareCommands::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_DECLARE_RECIPES, NoopDeclareRecipes::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_DECLARE_TAGS, NoopDeclareTags::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_STOP_SOUND, NoopStopSound::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_LOOK_AT, NoopLookAt::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SET_VIEW_CENTER, NoopSetViewCenter::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_VIEW_DISTANCE, NoopUpdateViewDistance::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_SOUND_ID, NoopEntitySound::new);
	}

	protected final ChunkSendIntervalPacketQueue chunkqueue = new ChunkSendIntervalPacketQueue();

	public PacketEncoder(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected RecyclableCollection<ClientBoundPacketData> processPackets(Channel channel, RecyclableCollection<ClientBoundPacketData> data) {
		RecyclableCollection<ClientBoundPacketData> allowed = chunkqueue.processPackets(data);
		long delay = chunkqueue.getUnlockDelay();
		if (delay != -1) {
			channel.eventLoop().schedule(
				() -> {
					chunkqueue.unlock();
					connection.sendPacket(ServerPlatform.get().getPacketFactory().createEmptyCustomPayloadPacket("pushqueue"));
				},
				delay, TimeUnit.NANOSECONDS
			);
		}
		return allowed;
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		chunkqueue.release();
	}

}
