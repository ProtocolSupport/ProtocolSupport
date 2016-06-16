package protocolsupport.protocol.packet.v_1_8;

import java.io.IOException;

import org.spigotmc.SneakyThrow;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.server.v1_10_R1.EnumProtocol;
import net.minecraft.server.v1_10_R1.EnumProtocolDirection;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_1_4__1_5__1_6__1_7__1_8.EncryptionRequest;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_1_7__1_8.LoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_1_7__1_8.LoginSuccess;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_1_8.SetCompression;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8.EntityStatus;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8.InventoryConfirmTransaction;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8.InventoryData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8.ScoreboardDisplay;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8.TimeUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8.WorldSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.InventorySetItems;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.InventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2.WorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_6__1_7__1_8.EntityAttach;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_6__1_7__1_8.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_6__1_7__1_8.SetPassengers;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8.Animation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8.Explosion;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8.GameStateChange;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8.KickDisconnect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8.SpawnExpOrb;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8.SpawnGlobal;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8.SpawnLiving;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8.Statistics;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8.TabComplete;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8__1_9_r1__1_9_r2.Respawn;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.BlockAction;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.BlockBreakAnimation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.BlockOpenSignEditor;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.Camera;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.Chat;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.Chunk;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.CollectEffect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.Entity;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.EntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.EntityEffectRemove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.EntityEquipment;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.EntityHeadRotation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.EntityLook;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.EntityMetadata;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.EntityRelMove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.EntityRelMoveLook;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.EntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.EntityTeleport;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.EntityVelocity;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.Login;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.Map;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.PlayerInfo;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.PlayerListHeaderFooter;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.Position;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.ResourcePack;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.ScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.ScoreboardScore;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.ScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.SetExperience;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.SetHealth;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.SpawnNamed;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.SpawnObject;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.SpawnPainting;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.SpawnPosition;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.Title;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.UnloadChunk;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.UseBed;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.WorldBorder;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.WorldEvent;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8.WorldParticle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1.BlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2.BlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2.BlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2.EntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2.InventoryOpen;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.v_1_7__1_8__1_9_r1.Pong;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.v_1_7__1_8__1_9_r1.ServerInfo;
import protocolsupport.protocol.pipeline.IPacketEncoder;
import protocolsupport.protocol.serializer.ChainedProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage;
import protocolsupport.protocol.storage.SharedStorage;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry;
import protocolsupport.protocol.utils.registry.MiddleTransformerRegistry.InitCallBack;
import protocolsupport.protocol.utils.registry.PacketIdTransformerRegistry;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PacketEncoder implements IPacketEncoder {

	private static final PacketIdTransformerRegistry packetIdRegistry = new PacketIdTransformerRegistry();
	static {
		packetIdRegistry.register(EnumProtocol.LOGIN, ClientBoundPacket.LOGIN_DISCONNECT_ID, 0x00);
		packetIdRegistry.register(EnumProtocol.LOGIN, ClientBoundPacket.LOGIN_ENCRYPTION_BEGIN_ID, 0x01);
		packetIdRegistry.register(EnumProtocol.LOGIN, ClientBoundPacket.LOGIN_SUCCESS_ID, 0x02);
		packetIdRegistry.register(EnumProtocol.LOGIN, ClientBoundPacket.LOGIN_SET_COMPRESSION_ID, 0x03);
		packetIdRegistry.register(EnumProtocol.STATUS, ClientBoundPacket.STATUS_SERVER_INFO_ID, 0x00);
		packetIdRegistry.register(EnumProtocol.STATUS, ClientBoundPacket.STATUS_PONG_ID, 0x01);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_KEEP_ALIVE_ID, 0x00);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_LOGIN_ID, 0x01);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CHAT_ID, 0x02);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_TIME_ID, 0x03);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID, 0x04);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_POSITION_ID, 0x05);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, 0x06);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_RESPAWN_ID, 0x07);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_POSITION_ID, 0x08);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_HELD_SLOT_ID, 0x09);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BED_ID, 0x0A);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ANIMATION_ID, 0x0B);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_NAMED_ID, 0x0C);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_COLLECT_EFFECT_ID, 0x0D);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, 0x0E);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_LIVING_ID, 0x0F);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_PAINTING_ID, 0x10);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_EXP_ORB_ID, 0x11);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_VELOCITY_ID, 0x12);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, 0x13);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_ID, 0x14);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID, 0x15);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_LOOK_ID, 0x16);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_LOOK_ID, 0x17);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, 0x18);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_HEAD_ROTATION_ID, 0x19);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_STATUS_ID, 0x1A);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTACH_ID, 0x1B);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_METADATA_ID, 0x1C);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, 0x1D);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_REMOVE_ID, 0x1E);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_EXPERIENCE_ID, 0x1F);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, 0x20);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, 0x21);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, 0x22);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, 0x23);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_ACTION_ID, 0x24);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_BREAK_ANIMATION_ID, 0x25);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_EXPLOSION_ID, 0x27);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_EVENT_ID, 0x28);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_SOUND_ID, 0x29);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_PARTICLES_ID, 0x2A);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_GAME_STATE_CHANGE_ID, 0x2B);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_WEATHER_ID, 0x2C);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_OPEN_ID, 0x2D);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_CLOSE_ID, 0x2E);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, 0x2F);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, 0x30);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_DATA_ID, 0x31);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_TRANSACTION_ID, 0x32);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.LEGACY_PLAY_UPDATE_SIGN_ID, 0x33);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_MAP_ID, 0x34);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_TILE_ID, 0x35);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SIGN_EDITOR_ID, 0x36);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_STATISTICS, 0x37);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_PLAYER_INFO_ID, 0x38);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ABILITIES_ID, 0x39);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_TAB_COMPLETE_ID, 0x3A);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_OBJECTIVE_ID, 0x3B);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_SCORE_ID, 0x3C);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_DISPLAY_SLOT_ID, 0x3D);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID, 0x3E);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, 0x3F);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_KICK_DISCONNECT_ID, 0x40);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CAMERA_ID, 0x43);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_BORDER_ID, 0x44);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_TITLE, 0x45);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_PLAYER_LIST_HEADER_FOOTER, 0x47);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_RESOURCE_PACK_ID, 0x48);	
	}

	private final MiddleTransformerRegistry<ClientBoundMiddlePacket<RecyclableCollection<PacketData>>> registry = new MiddleTransformerRegistry<>();
	{
		try {
			registry.register(EnumProtocol.LOGIN, ClientBoundPacket.LOGIN_SUCCESS_ID, LoginSuccess.class);
			registry.register(EnumProtocol.LOGIN, ClientBoundPacket.LOGIN_ENCRYPTION_BEGIN_ID, EncryptionRequest.class);
			registry.register(EnumProtocol.LOGIN, ClientBoundPacket.LOGIN_DISCONNECT_ID, LoginDisconnect.class);
			registry.register(EnumProtocol.LOGIN, ClientBoundPacket.LOGIN_SET_COMPRESSION_ID, SetCompression.class);
			registry.register(EnumProtocol.STATUS, ClientBoundPacket.STATUS_SERVER_INFO_ID, ServerInfo.class);
			registry.register(EnumProtocol.STATUS, ClientBoundPacket.STATUS_PONG_ID, Pong.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_KEEP_ALIVE_ID, KeepAlive.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_LOGIN_ID, Login.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CHAT_ID, Chat.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_TIME_ID, TimeUpdate.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID, EntityEquipment.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_POSITION_ID, SpawnPosition.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, SetHealth.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_RESPAWN_ID, Respawn.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_POSITION_ID, Position.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_HELD_SLOT_ID, HeldSlot.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BED_ID, UseBed.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ANIMATION_ID, Animation.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_NAMED_ID, SpawnNamed.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_COLLECT_EFFECT_ID, CollectEffect.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, SpawnObject.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_LIVING_ID, SpawnLiving.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_PAINTING_ID, SpawnPainting.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_EXP_ORB_ID, SpawnExpOrb.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_VELOCITY_ID, EntityVelocity.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, EntityDestroy.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_ID, Entity.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID, EntityRelMove.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_LOOK_ID, EntityLook.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_LOOK_ID, EntityRelMoveLook.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, EntityTeleport.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_HEAD_ROTATION_ID, EntityHeadRotation.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_STATUS_ID, EntityStatus.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTACH_ID, EntityAttach.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_METADATA_ID, EntityMetadata.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, EntityEffectAdd.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_REMOVE_ID, EntityEffectRemove.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_EXPERIENCE_ID, SetExperience.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, EntitySetAttributes.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, Chunk.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, BlockChangeMulti.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, BlockChangeSingle.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_ACTION_ID, BlockAction.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_BREAK_ANIMATION_ID, BlockBreakAnimation.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_EXPLOSION_ID, Explosion.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_EVENT_ID, WorldEvent.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_SOUND_ID, WorldSound.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_CUSTOM_SOUND, WorldCustomSound.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_PARTICLES_ID, WorldParticle.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_GAME_STATE_CHANGE_ID, GameStateChange.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_WEATHER_ID, SpawnGlobal.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_OPEN_ID, InventoryOpen.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_CLOSE_ID, InventoryClose.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, InventorySetSlot.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, InventorySetItems.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_DATA_ID, InventoryData.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_TRANSACTION_ID, InventoryConfirmTransaction.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_MAP_ID, Map.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_TILE_ID, BlockTileUpdate.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SIGN_EDITOR_ID, BlockOpenSignEditor.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_STATISTICS, Statistics.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_PLAYER_INFO_ID, PlayerInfo.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ABILITIES_ID, PlayerAbilities.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_TAB_COMPLETE_ID, TabComplete.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_OBJECTIVE_ID, ScoreboardObjective.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_SCORE_ID, ScoreboardScore.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_DISPLAY_SLOT_ID, ScoreboardDisplay.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID, ScoreboardTeam.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, CustomPayload.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_RESOURCE_PACK_ID, ResourcePack.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_KICK_DISCONNECT_ID, KickDisconnect.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CAMERA_ID, Camera.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_PLAYER_LIST_HEADER_FOOTER, PlayerListHeaderFooter.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SET_PASSENGERS, SetPassengers.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_TITLE, Title.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_BORDER_ID, WorldBorder.class);
			registry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CHUNK_UNLOAD, UnloadChunk.class);
			registry.setCallBack(new InitCallBack<ClientBoundMiddlePacket<RecyclableCollection<PacketData>>>() {
				@Override
				public void onInit(ClientBoundMiddlePacket<RecyclableCollection<PacketData>> object) {
					object.setSharedStorage(sharedstorage);
					object.setLocalStorage(storage);
				}
			});
		} catch (Throwable t) {
			SneakyThrow.sneaky(t);
		}
	}

	protected final SharedStorage sharedstorage;
	protected final LocalStorage storage = new LocalStorage();
	public PacketEncoder(SharedStorage sharedstorage) {
		this.sharedstorage = sharedstorage;
	}

	private final ChainedProtocolSupportPacketDataSerializer middlebuffer = new ChainedProtocolSupportPacketDataSerializer();

	@Override
	public void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(ChannelUtils.CURRENT_PROTOCOL_KEY).get();
		final Integer packetId = currentProtocol.a(EnumProtocolDirection.CLIENTBOUND, packet);
		if (packetId == null) {
			throw new IOException("Can't serialize unregistered packet");
		}
		ClientBoundMiddlePacket<RecyclableCollection<PacketData>> packetTransformer = registry.getTransformer(currentProtocol, packetId);
		if (packetTransformer != null) {
			middlebuffer.clear();
			packet.b(middlebuffer.getNativeSerializer());
			if (packetTransformer.needsPlayer()) {
				packetTransformer.setPlayer(ChannelUtils.getBukkitPlayer(channel));
			}
			packetTransformer.readFromServerData(middlebuffer);
			packetTransformer.handle();
			RecyclableCollection<PacketData> data = packetTransformer.toData(ProtocolVersion.MINECRAFT_1_8);
			try {
				for (PacketData packetdata : data) {
					ByteBuf senddata = Allocator.allocateBuffer();
					ChannelUtils.writeVarInt(senddata, packetIdRegistry.getNewPacketId(currentProtocol, packetdata.getPacketId()));
					senddata.writeBytes(packetdata);
					ctx.write(senddata);
				}
				ctx.flush();
			} finally {
				for (PacketData packetdata : data) {
					packetdata.recycle();
				}
				data.recycle();
			}
		}
	}

}
