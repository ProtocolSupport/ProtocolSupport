package protocolsupport.protocol.transformer.v_1_6;

import java.io.IOException;

import org.spigotmc.SneakyThrow;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.core.IPacketEncoder;
import protocolsupport.protocol.storage.LocalStorage;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.login.v_1_4_1_5_1_6.LoginDisconnect;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.login.v_1_4_1_5_1_6_1_7.EncryptionRequest;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7.InventoryOpen;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7.ScoreboardObjective;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7.ScoreboardScore;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7.ScoreboardTeam;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7.WorldParticle;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.Animation;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.BlockAction;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.BlockBreakAnimation;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.BlockChangeSingle;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.EntityTeleport;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.Explosion;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.GameStateChange;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.HeldSlot;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.KickDisconnect;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.Login;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.Map;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.Respawn;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.SpawnExpOrb;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.SpawnGlobal;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.SpawnLiving;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.SpawnNamed;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.SpawnObject;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.SpawnPainting;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.SpawnPosition;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.TabComplete;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.UseBed;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6.WorldSound;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.BlockChangeMulti;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.BlockSignUpdate;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.BlockTileUpdate;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.ChunkMulti;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.ChunkSingle;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.CollectEffect;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.CustomPayload;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.Entity;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.EntityDestroy;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.EntityEffectAdd;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.EntityEffectRemove;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.EntityEquipment;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.EntityHeadRotation;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.EntityLook;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.EntityMetadata;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.EntityRelMove;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.EntityRelMoveLook;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.EntityStatus;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.EntityVelocity;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.InventoryClose;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.InventoryConfirmTransaction;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.InventoryData;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.InventorySetItems;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.InventorySetSlot;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.KeepAlive;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.PlayerInfo;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.ScoreboardDisplay;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.SetExperience;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.TimeUpdate;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7.WorldEvent;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_6.BlockOpenSignEditor;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_6.Chat;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_6.Position;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_6_1_7.EntityAttach;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_6_1_7.EntitySetAttributes;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_6_1_7.PlayerAbilities;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_6_1_7.SetHealth;
import protocolsupport.protocol.transformer.middlepacketimpl.clientbound.status.v_1_5_1_6.ServerInfo;
import protocolsupport.protocol.transformer.utils.registry.MiddleTransformerRegistry;
import protocolsupport.protocol.transformer.utils.registry.PacketIdTransformerRegistry;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class PacketEncoder implements IPacketEncoder {

	private static final EnumProtocolDirection direction = EnumProtocolDirection.CLIENTBOUND;
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private static final PacketIdTransformerRegistry packetIdRegistry = new PacketIdTransformerRegistry();
	static {
		packetIdRegistry.register(EnumProtocol.LOGIN, ClientBoundPacket.LOGIN_DISCONNECT_ID, 0xFF);
		packetIdRegistry.register(EnumProtocol.LOGIN, ClientBoundPacket.LOGIN_ENCRYPTION_BEGIN_ID, 0xFD);
		packetIdRegistry.register(EnumProtocol.STATUS, ClientBoundPacket.STATUS_SERVER_INFO_ID, 0xFF);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_KEEP_ALIVE_ID, 0x00);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_LOGIN_ID, 0x01);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CHAT_ID, 0x03);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_TIME_ID, 0x04);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID, 0x05);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_POSITION_ID, 0x06);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, 0x08);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_RESPAWN_ID, 0x09);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_POSITION_ID, 0x0D);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_HELD_SLOT_ID, 0x10);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BED_ID, 0x11);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ANIMATION_ID, 0x12);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_NAMED_ID, 0x14);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_COLLECT_EFFECT_ID, 0x16);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, 0x17);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_LIVING_ID, 0x18);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_PAINTING_ID, 0x19);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_EXP_ORB_ID, 0x1A);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_VELOCITY_ID, 0x1C);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, 0x1D);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_ID, 0x1E);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID, 0x1F);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_LOOK_ID, 0x20);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_LOOK_ID, 0x21);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, 0x22);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_HEAD_ROTATION_ID, 0x23);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_STATUS_ID, 0x26);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTACH_ID, 0x27);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_METADATA_ID, 0x28);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, 0x29);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_REMOVE_ID, 0x2A);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_EXPERIENCE_ID, 0x2B);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, 0x2C);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, 0x33);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, 0x34);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, 0x35);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_ACTION_ID, 0x36);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_BREAK_ANIMATION_ID, 0x37);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CHUNK_MULTI_ID, 0x38);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_EXPLOSION_ID, 0x3C);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_EVENT_ID, 0x3D);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_SOUND_ID, 0x3E);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_PARTICLES_ID, 0x3F);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_GAME_STATE_CHANGE_ID, 0x46);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_WEATHER_ID, 0x47);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_OPEN_ID, 0x64);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_CLOSE_ID, 0x65);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, 0x67);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, 0x68);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_DATA_ID, 0x69);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_TRANSACTION_ID, 0x6A);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_SIGN_ID, 0x82);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_MAP_ID, 0x83);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_TILE_ID, 0x84);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SIGN_EDITOR_ID, 0x85);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_PLAYER_INFO_ID, 0xC9);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ABILITIES_ID, 0xCA);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_TAB_COMPLETE_ID, 0xCB);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_OBJECTIVE_ID, 0xCE);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_SCORE_ID, 0xCF);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_DISPLAY_SLOT_ID, 0xD0);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID, 0xD1);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, 0xFA);
		packetIdRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_KICK_DISCONNECT_ID, 0xFF);
	}

	private final MiddleTransformerRegistry<ClientBoundMiddlePacket<RecyclableCollection<PacketData>>> dataRemapperRegistry = new MiddleTransformerRegistry<>();
	{
		try {
			dataRemapperRegistry.register(EnumProtocol.LOGIN, ClientBoundPacket.LOGIN_DISCONNECT_ID, LoginDisconnect.class);
			dataRemapperRegistry.register(EnumProtocol.LOGIN, ClientBoundPacket.LOGIN_ENCRYPTION_BEGIN_ID, EncryptionRequest.class);
			dataRemapperRegistry.register(EnumProtocol.STATUS, ClientBoundPacket.STATUS_SERVER_INFO_ID, ServerInfo.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_KEEP_ALIVE_ID, KeepAlive.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_LOGIN_ID, Login.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CHAT_ID, Chat.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_TIME_ID, TimeUpdate.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID, EntityEquipment.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_POSITION_ID, SpawnPosition.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, SetHealth.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_RESPAWN_ID, Respawn.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_POSITION_ID, Position.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_HELD_SLOT_ID, HeldSlot.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BED_ID, UseBed.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ANIMATION_ID, Animation.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_NAMED_ID, SpawnNamed.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_COLLECT_EFFECT_ID, CollectEffect.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, SpawnObject.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_LIVING_ID, SpawnLiving.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_PAINTING_ID, SpawnPainting.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_EXP_ORB_ID, SpawnExpOrb.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_VELOCITY_ID, EntityVelocity.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, EntityDestroy.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_ID, Entity.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID, EntityRelMove.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_LOOK_ID, EntityLook.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_LOOK_ID, EntityRelMoveLook.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, EntityTeleport.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_HEAD_ROTATION_ID, EntityHeadRotation.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_STATUS_ID, EntityStatus.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTACH_ID, EntityAttach.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_METADATA_ID, EntityMetadata.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, EntityEffectAdd.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_REMOVE_ID, EntityEffectRemove.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_EXPERIENCE_ID, SetExperience.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, EntitySetAttributes.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, ChunkSingle.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, BlockChangeMulti.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, BlockChangeSingle.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_ACTION_ID, BlockAction.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_BLOCK_BREAK_ANIMATION_ID, BlockBreakAnimation.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CHUNK_MULTI_ID, ChunkMulti.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_EXPLOSION_ID, Explosion.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_EVENT_ID, WorldEvent.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_SOUND_ID, WorldSound.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WORLD_PARTICLES_ID, WorldParticle.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_GAME_STATE_CHANGE_ID, GameStateChange.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SPAWN_WEATHER_ID, SpawnGlobal.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_OPEN_ID, InventoryOpen.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_CLOSE_ID, InventoryClose.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, InventorySetSlot.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, InventorySetItems.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_DATA_ID, InventoryData.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_TRANSACTION_ID, InventoryConfirmTransaction.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_SIGN_ID, BlockSignUpdate.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_MAP_ID, Map.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_TILE_ID, BlockTileUpdate.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SIGN_EDITOR_ID, BlockOpenSignEditor.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_PLAYER_INFO_ID, PlayerInfo.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ABILITIES_ID, PlayerAbilities.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_TAB_COMPLETE_ID, TabComplete.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_OBJECTIVE_ID, ScoreboardObjective.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_SCORE_ID, ScoreboardScore.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_DISPLAY_SLOT_ID, ScoreboardDisplay.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_TEAM_ID, ScoreboardTeam.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, CustomPayload.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_KICK_DISCONNECT_ID, KickDisconnect.class);
		} catch (Throwable t) {
			SneakyThrow.sneaky(t);
		}
	}

	private final ProtocolVersion version;
	public PacketEncoder(ProtocolVersion version) {
		this.version = version;
	}

	private final LocalStorage storage = new LocalStorage();
	private final PacketDataSerializer serverdata = new PacketDataSerializer(Unpooled.buffer(), ProtocolVersion.getLatest());

	@Override
	public void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
		final Integer packetId = currentProtocol.a(direction, packet);
		if (packetId == null) {
			throw new IOException("Can't serialize unregistered packet");
		}
		ClientBoundMiddlePacket<RecyclableCollection<PacketData>> packetTransformer = dataRemapperRegistry.getTransformer(currentProtocol, packetId);
		if (packetTransformer != null) {
			serverdata.clear();
			packet.b(serverdata);
			if (packetTransformer.needsPlayer()) {
				packetTransformer.setPlayer(ChannelUtils.getBukkitPlayer(channel));
			}
			packetTransformer.readFromServerData(serverdata);
			packetTransformer.setLocalStorage(storage);
			packetTransformer.handle();
			RecyclableCollection<PacketData> data = packetTransformer.toData(version);
			try {
				for (PacketData packetdata : data) {
					ByteBuf senddata = Allocator.allocateBuffer();
					senddata.writeByte(packetIdRegistry.getNewPacketId(currentProtocol, packetdata.getPacketId()));
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
