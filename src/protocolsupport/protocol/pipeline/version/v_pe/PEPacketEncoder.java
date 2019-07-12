package protocolsupport.protocol.pipeline.version.v_pe;

import io.netty.buffer.ByteBuf;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe.LoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe.LoginSuccess;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Animation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockAction;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockBreakAnimation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.BlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.ChangeDimension;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.ChangeGameState;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Chat;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Chunk;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.CollectEffect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.CraftingData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.DeclareCommands;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityEffectRemove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityEquipment;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityHeadRotation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityLeash;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityLook;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityRelMove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityRelMoveLook;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityStatus;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityTeleport;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityVelocity;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Explosion;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventoryConfirmTransaction;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventoryData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventoryOpen;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventorySetItems;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.InventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.KickDisconnect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.PlayerListSetEntry;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.ServerDifficulty;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SetExperience;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SetHealth;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SetPosition;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SetViewCenter;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnExpOrb;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnGlobal;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnLiving;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnNamed;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnObject;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnPainting;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnPosition;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.StartGame;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.StopSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.TimeUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Title;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.UnloadChunk;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.VehiclePassengers;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.WorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.WorldEvent;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.WorldParticle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.WorldSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.v_pe.ServerInfo;
import protocolsupport.protocol.pipeline.version.util.encoder.AbstractPacketEncoder;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class PEPacketEncoder extends AbstractPacketEncoder {

	{
		for (int i = 0; i < 255; i++) {
			registry.register(NetworkState.PLAY, i, Noop::new);
		}
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_DISCONNECT_ID, LoginDisconnect::new);
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_SUCCESS_ID, LoginSuccess::new);
		registry.register(NetworkState.STATUS, ClientBoundPacket.STATUS_SERVER_INFO_ID, ServerInfo::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_KEEP_ALIVE_ID, KeepAlive::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_LOGIN_ID, StartGame::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, CustomPayload::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, Chunk::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_POSITION_ID, SetPosition::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHAT_ID, Chat::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_KICK_DISCONNECT_ID, KickDisconnect::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, SetHealth::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, BlockChangeSingle::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_TIME_ID, TimeUpdate::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_POSITION_ID, SpawnPosition::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, EntitySetAttributes::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SERVER_DIFFICULTY_ID, ServerDifficulty::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, BlockChangeMulti::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_EXPERIENCE_ID, SetExperience::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_RESPAWN_ID, ChangeDimension::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHUNK_UNLOAD_ID, UnloadChunk::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_LIVING_ID, SpawnLiving::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, SpawnObject::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_COLLECT_EFFECT_ID, CollectEffect::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_VELOCITY_ID, EntityVelocity::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, EntityTeleport::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_HEAD_ROTATION_ID, EntityHeadRotation::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_LOOK_ID, EntityLook::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID, EntityRelMove::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_REL_MOVE_LOOK_ID, EntityRelMoveLook::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, EntityDestroy::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_PLAYER_INFO_ID, PlayerListSetEntry::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_NAMED_ID, SpawnNamed::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_GAME_STATE_CHANGE_ID, ChangeGameState::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ANIMATION_ID, Animation::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_EVENT_ID, WorldEvent::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_PARTICLES_ID, WorldParticle::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_WEATHER_ID, SpawnGlobal::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ABILITIES_ID, PlayerAbilities::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_STATUS_ID, EntityStatus::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_TITLE_ID, Title::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_EXP_ORB_ID, SpawnExpOrb::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_PAINTING_ID, SpawnPainting::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, EntityEffectAdd::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_REMOVE_ID, EntityEffectRemove::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SET_PASSENGERS_ID, VehiclePassengers::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_METADATA_ID, EntityMetadata::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_LEASH_ID, EntityLeash::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_EXPLOSION_ID, Explosion::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_SOUND_ID, WorldSound::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_CUSTOM_SOUND_ID, WorldCustomSound::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_STOP_SOUND, StopSound::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_BREAK_ANIMATION_ID, BlockBreakAnimation::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_TILE_ID, BlockTileUpdate::new);
		//registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_MAP_ID, Map::new); //TODO: PE maps
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID, EntityEquipment::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_ACTION_ID, BlockAction::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_HELD_SLOT_ID, HeldSlot::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_OPEN_ID, InventoryOpen::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_CLOSE_ID, InventoryClose::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, InventorySetItems::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, InventorySetSlot::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_DATA_ID, InventoryData::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_TRANSACTION_ID, InventoryConfirmTransaction::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_DECLARE_RECIPES, CraftingData::new);
		//registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BED_ID, UseBed::new); //TODO: fix PE use beds
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_DECLARE_COMMANDS, DeclareCommands::new);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SET_VIEW_CENTER, SetViewCenter::new);
	}

	public PEPacketEncoder(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writePacketId(ByteBuf to, int packetId) {
		sWritePacketId(to, packetId);
	}

	public static void sWritePacketId(ByteBuf to, int packetId) {
		VarNumberSerializer.writeVarInt(to, packetId);
	}

	@Override
	protected int getNewPacketId(NetworkState currentProtocol, int oldPacketId) {
		return oldPacketId;
	}

	public static class Noop extends ClientBoundMiddlePacket {

		public Noop(ConnectionImpl connection) {
			super(connection);
		}

		@Override
		public void readFromServerData(ByteBuf serverdata) {
			serverdata.skipBytes(serverdata.readableBytes());
		}

		@Override
		public RecyclableCollection<ClientBoundPacketData> toData() {
			return RecyclableEmptyList.get();
		}

	}

}
