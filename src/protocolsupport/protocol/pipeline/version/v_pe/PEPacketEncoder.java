package protocolsupport.protocol.pipeline.version.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.Connection;
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
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.ChangeGameState;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Chat;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Chunk;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.CollectEffect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityEffectRemove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityEquipment;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityLeash;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.EntityMetadata;
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
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.KickDisconnect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Login;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Map;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.PlayerInfo;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Position;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Respawn;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.ServerDifficulty;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SetExperience;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SetHealth;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SetPassengers;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnExpOrb;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnGlobal;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnLiving;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnNamed;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnObject;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnPainting;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.SpawnPosition;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.TimeUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Title;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.UnloadChunk;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.WorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.WorldEvent;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.WorldParticle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.WorldSound;
import protocolsupport.protocol.pipeline.version.AbstractLegacyPacketEncoder;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.zplatform.network.NetworkState;

public class PEPacketEncoder extends AbstractLegacyPacketEncoder {

	{
		for (int i = 0; i < 255; i++) {
			registry.register(NetworkState.PLAY, i, Noop.class);
		}
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_DISCONNECT_ID, LoginDisconnect.class);
		registry.register(NetworkState.LOGIN, ClientBoundPacket.LOGIN_SUCCESS_ID, LoginSuccess.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_LOGIN_ID, Login.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, CustomPayload.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, Chunk.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_POSITION_ID, Position.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHAT_ID, Chat.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_KICK_DISCONNECT_ID, KickDisconnect.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, SetHealth.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, BlockChangeSingle.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_TIME_ID, TimeUpdate.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_POSITION_ID, SpawnPosition.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, EntitySetAttributes.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SERVER_DIFFICULTY_ID, ServerDifficulty.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, BlockChangeMulti.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_EXPERIENCE_ID, SetExperience.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_RESPAWN_ID, Respawn.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_CHUNK_UNLOAD_ID, UnloadChunk.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_LIVING_ID, SpawnLiving.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, SpawnObject.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_COLLECT_EFFECT_ID, CollectEffect.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_VELOCITY_ID, EntityVelocity.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, EntityTeleport.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, EntityDestroy.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_PLAYER_INFO_ID, PlayerInfo.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_NAMED_ID, SpawnNamed.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_GAME_STATE_CHANGE_ID, ChangeGameState.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ANIMATION_ID, Animation.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_EVENT_ID, WorldEvent.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_PARTICLES_ID, WorldParticle.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_WEATHER_ID, SpawnGlobal.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ABILITIES_ID, PlayerAbilities.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_STATUS_ID, EntityStatus.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_TITLE_ID, Title.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_EXP_ORB_ID, SpawnExpOrb.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SPAWN_PAINTING_ID, SpawnPainting.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, EntityEffectAdd.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EFFECT_REMOVE_ID, EntityEffectRemove.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_SET_PASSENGERS_ID, SetPassengers.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_METADATA_ID, EntityMetadata.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_LEASH_ID, EntityLeash.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_EXPLOSION_ID, Explosion.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_SOUND_ID, WorldSound.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WORLD_CUSTOM_SOUND_ID, WorldCustomSound.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_BREAK_ANIMATION_ID, BlockBreakAnimation.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_HELD_SLOT_ID, HeldSlot.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_OPEN_ID, InventoryOpen.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_CLOSE_ID, InventoryClose.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, InventorySetItems.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, InventorySetSlot.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_UPDATE_TILE_ID, BlockTileUpdate.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_MAP_ID, Map.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID, EntityEquipment.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_BLOCK_ACTION_ID, BlockAction.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_DATA_ID, InventoryData.class);
		registry.register(NetworkState.PLAY, ClientBoundPacket.PLAY_WINDOW_TRANSACTION_ID, InventoryConfirmTransaction.class);
	}

	public PEPacketEncoder(Connection connection, NetworkDataCache storage) {
		super(connection, storage);
	}

	@Override
	protected void writePacketId(ByteBuf to, int packetId) {
		VarNumberSerializer.writeVarInt(to, packetId);
		to.writeByte(0);
		to.writeByte(0);
	}

	@Override
	protected int getNewPacketId(NetworkState currentProtocol, int oldPacketId) {
		return oldPacketId;
	}

	public static class Noop extends ClientBoundMiddlePacket {

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
