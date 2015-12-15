package protocolsupport.protocol.transformer.v_1_5;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.util.Collection;

import org.spigotmc.SneakyThrow;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.MinecraftServer;
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
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.BlockChangeMulti;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.BlockSignUpdate;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.ChunkMulti;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.ChunkSingle;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.CollectEffect;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.Entity;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.EntityDestroy;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.EntityEffectAdd;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.EntityEffectRemove;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.EntityEquipment;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.EntityHeadRotation;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.EntityLook;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.EntityMetadata;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.EntityRelMove;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.EntityVelocity;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.InventoryData;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.InventoryOpen;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.InventorySetItems;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.InventorySetSlot;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.KeepAlive;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.PlayerInfo;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.ScoreboardObjective;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.ScoreboardScore;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.ScoreboardTeam;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.SetExperience;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.SpawnPainting;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.WorldEvent;
import protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play.WorldParticle;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5.clientbound.play.Chat;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5.clientbound.play.CustomPayload;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5.clientbound.play.PlayerAbilities;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5.clientbound.play.Position;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5.clientbound.play.SetHealth;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.login.LoginDisconnect;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.Animation;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.BlockAction;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.BlockBreakAnimation;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.BlockChangeSingle;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.BlockTileUpdate;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.EntityTeleport;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.Explosion;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.GameStateChange;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.HeldSlot;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.KickDisconnect;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.Login;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.Map;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.Respawn;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.SpawnExpOrb;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.SpawnGlobal;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.SpawnLiving;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.SpawnNamed;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.SpawnObject;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.SpawnPosition;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.TabComplete;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.UseBed;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.play.WorldSound;
import protocolsupport.protocol.transformer.middlepacketimpl.v_1_5_1_6.clientbound.status.ServerInfo;
import protocolsupport.protocol.transformer.utils.registry.ClientBoundMiddleTransformerRegistry;
import protocolsupport.protocol.transformer.utils.registry.ClientBoundPacketIdTransformerRegistry;
import protocolsupport.utils.Utils;

public class PacketEncoder implements IPacketEncoder {

	private static final EnumProtocolDirection direction = EnumProtocolDirection.CLIENTBOUND;
	private static final AttributeKey<EnumProtocol> currentStateAttrKey = NetworkManager.c;

	private static final ClientBoundPacketIdTransformerRegistry packetIdRegistry = new ClientBoundPacketIdTransformerRegistry();
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
	private static final ClientBoundMiddleTransformerRegistry<Collection<PacketData>> dataRemapperRegistry = new ClientBoundMiddleTransformerRegistry<>();
	static {
		try {
			dataRemapperRegistry.register(EnumProtocol.LOGIN, ClientBoundPacket.LOGIN_DISCONNECT_ID, LoginDisconnect.class);
			dataRemapperRegistry.register(EnumProtocol.STATUS, ClientBoundPacket.STATUS_SERVER_INFO_ID, ServerInfo.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_KEEP_ALIVE_ID, KeepAlive.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_LOGIN_ID, Login.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_CHAT_ID, Chat.class);
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
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_LOOK_ID, EntityRelMove.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, EntityTeleport.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_HEAD_ROTATION_ID, EntityHeadRotation.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_METADATA_ID, EntityMetadata.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, EntityEffectAdd.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_REMOVE_ID, EntityEffectRemove.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_EXPERIENCE_ID, SetExperience.class);
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
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, InventorySetSlot.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, InventorySetItems.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_WINDOW_DATA_ID, InventoryData.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_SIGN_ID, BlockSignUpdate.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_MAP_ID, Map.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_UPDATE_TILE_ID, BlockTileUpdate.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_PLAYER_INFO_ID, PlayerInfo.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_ABILITIES_ID, PlayerAbilities.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_TAB_COMPLETE_ID, TabComplete.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_OBJECTIVE_ID, ScoreboardObjective.class);
			dataRemapperRegistry.register(EnumProtocol.PLAY, ClientBoundPacket.PLAY_SCOREBOARD_SCORE_ID, ScoreboardScore.class);
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

	@Override
	public void encode(ChannelHandlerContext ctx, Packet<PacketListener> packet, ByteBuf output) throws Exception {
		Channel channel = ctx.channel();
		EnumProtocol currentProtocol = channel.attr(currentStateAttrKey).get();
		final Integer packetId = currentProtocol.a(direction, packet);
		if (packetId == null) {
			throw new IOException("Can't serialize unregistered packet");
		}
		ClientBoundMiddlePacket<Collection<PacketData>> packetTransformer = dataRemapperRegistry.getTransformer(currentProtocol, packetId);
		try {
			if (packetTransformer != null) {
				PacketDataSerializer serverdata = PacketDataSerializer.createNew(ProtocolVersion.getLatest());
				packet.b(serverdata);
				try {
					if (packetTransformer.needsPlayer()) {
						packetTransformer.setPlayer(Utils.getPlayer(channel));
					}
					packetTransformer.readFromServerData(serverdata);
					packetTransformer.handle(storage);
					Collection<PacketData> data = packetTransformer.toData(version);
					try {
						for (PacketData packetdata : data) {
							PacketDataSerializer singlepacketdata = PacketDataSerializer.createNew(version);
							singlepacketdata.writeByte(packetIdRegistry.getNewPacketId(currentProtocol, packetdata.getPacketId()));
							singlepacketdata.writeBytes(packetdata.getData());
							ctx.write(singlepacketdata);
						}
						ctx.flush();
					} finally {
						for (PacketData packetdata : data) {
							packetdata.getData().release();
						}
					}
				} finally {
					serverdata.release();
				}
			} else {
				int newPacketId = packetIdRegistry.getNewPacketId(currentProtocol, packetId);
				if (newPacketId != -1) {
					PacketDataSerializer outserializer = new PacketDataSerializer(output, version);
					outserializer.writeByte(newPacketId);
					packet.b(outserializer);
				}
			}
		} catch (Throwable t) {
			if (MinecraftServer.getServer().isDebugging()) {
				t.printStackTrace();
			}
			throw t;
		}
	}

}
