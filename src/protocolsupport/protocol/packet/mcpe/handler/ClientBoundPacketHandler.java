package protocolsupport.protocol.packet.mcpe.handler;

import gnu.trove.map.hash.TIntObjectHashMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.mcpe.UDPNetworkManager;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.BlockChangeMulti;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.BlockChangeSingle;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.Chat;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.ChunkSingle;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.CollectEffect;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.EntityAttach;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.EntityDestroy;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.EntityEffectAdd;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.EntityEquipment;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.EntityMetadata;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.EntityRelUpdate;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.EntityStatus;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.EntityTeleport;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.EntityVelocity;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.Explosion;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.GameStateChange;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.InventoryClose;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.InventoryOpen;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.InventorySetItems;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.InventorySetSlot;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.KeepAlive;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.Login;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.PlayerInfo;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.Position;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.Respawn;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.SetExperience;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.SetHealth;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.SpawnExpOrb;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.SpawnGlobal;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.SpawnLiving;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.SpawnNamed;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.SpawnObject;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.SpawnPainting;
import protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound.TimeUpdate;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.RecyclablePacketDataSerializer;
import protocolsupport.protocol.storage.LocalStorage;
import protocolsupport.protocol.storage.SharedStorage;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

import net.minecraft.server.v1_9_R2.EnumProtocol;
import net.minecraft.server.v1_9_R2.EnumProtocolDirection;
import net.minecraft.server.v1_9_R2.Packet;

public class ClientBoundPacketHandler {

	private final SharedStorage sharedstorage;
	private final UDPNetworkManager networkManager;
	public ClientBoundPacketHandler(UDPNetworkManager networkManager, SharedStorage sharedstorage) {
		this.networkManager = networkManager;
		this.sharedstorage = sharedstorage;
	}

	private final LocalStorage storage = new LocalStorage();

	private boolean spawned;

	public boolean canSpawnPlayer() {
		return !isSpawned() && storage.getPEStorage().hasPlayerChunkMatch();
	}

	public void setSpawned() {
		spawned = true;
	}

	public boolean isSpawned() {
		return spawned;
	}

	private static final TIntObjectHashMap<Class<? extends ClientBoundMiddlePacket<RecyclableCollection<? extends ClientboundPEPacket>>>> registry = new TIntObjectHashMap<>();
	static {
		registry.put(ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, BlockChangeMulti.class);
		registry.put(ClientBoundPacket.PLAY_BLOCK_CHANGE_SINGLE_ID, BlockChangeSingle.class);
		registry.put(ClientBoundPacket.PLAY_CHAT_ID, Chat.class);
		registry.put(ClientBoundPacket.PLAY_CHUNK_SINGLE_ID, ChunkSingle.class);
		registry.put(ClientBoundPacket.PLAY_COLLECT_EFFECT_ID, CollectEffect.class);
		registry.put(ClientBoundPacket.PLAY_ENTITY_ATTACH_ID, EntityAttach.class);
		registry.put(ClientBoundPacket.PLAY_ENTITY_DESTROY_ID, EntityDestroy.class);
		registry.put(ClientBoundPacket.PLAY_ENTITY_EFFECT_ADD_ID, EntityEffectAdd.class);
		registry.put(ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID, EntityEquipment.class);
		registry.put(ClientBoundPacket.PLAY_ENTITY_METADATA_ID, EntityMetadata.class);
		registry.put(ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID, EntityRelUpdate.class);
		registry.put(ClientBoundPacket.PLAY_ENTITY_REL_MOVE_LOOK_ID, EntityRelUpdate.class);
		registry.put(ClientBoundPacket.PLAY_ENTITY_LOOK_ID, EntityRelUpdate.class);
		registry.put(ClientBoundPacket.PLAY_ENTITY_STATUS_ID, EntityStatus.class);
		registry.put(ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, EntityTeleport.class);
		registry.put(ClientBoundPacket.PLAY_ENTITY_VELOCITY_ID, EntityVelocity.class);
		registry.put(ClientBoundPacket.PLAY_EXPLOSION_ID, Explosion.class);
		registry.put(ClientBoundPacket.PLAY_GAME_STATE_CHANGE_ID, GameStateChange.class);
		registry.put(ClientBoundPacket.PLAY_WINDOW_CLOSE_ID, InventoryClose.class);
		registry.put(ClientBoundPacket.PLAY_WINDOW_OPEN_ID, InventoryOpen.class);
		registry.put(ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, InventorySetItems.class);
		registry.put(ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, InventorySetSlot.class);
		registry.put(ClientBoundPacket.PLAY_KEEP_ALIVE_ID, KeepAlive.class);
		registry.put(ClientBoundPacket.PLAY_LOGIN_ID, Login.class);
		registry.put(ClientBoundPacket.PLAY_PLAYER_INFO_ID, PlayerInfo.class);
		registry.put(ClientBoundPacket.PLAY_POSITION_ID, Position.class);
		registry.put(ClientBoundPacket.PLAY_RESPAWN_ID, Respawn.class);
		registry.put(ClientBoundPacket.PLAY_EXPERIENCE_ID, SetExperience.class);
		registry.put(ClientBoundPacket.PLAY_UPDATE_HEALTH_ID, SetHealth.class);
		registry.put(ClientBoundPacket.PLAY_SPAWN_EXP_ORB_ID, SpawnExpOrb.class);
		registry.put(ClientBoundPacket.PLAY_SPAWN_WEATHER_ID, SpawnGlobal.class);
		registry.put(ClientBoundPacket.PLAY_SPAWN_LIVING_ID, SpawnLiving.class);
		registry.put(ClientBoundPacket.PLAY_SPAWN_NAMED_ID, SpawnNamed.class);
		registry.put(ClientBoundPacket.PLAY_SPAWN_OBJECT_ID, SpawnObject.class);
		registry.put(ClientBoundPacket.PLAY_SPAWN_PAINTING_ID, SpawnPainting.class);
		registry.put(ClientBoundPacket.PLAY_UPDATE_TIME_ID, TimeUpdate.class);
	}

	@SuppressWarnings({ "rawtypes" })
	public RecyclableCollection<? extends ClientboundPEPacket> transform(Packet packet) throws Exception {
		RecyclablePacketDataSerializer serverdata = RecyclablePacketDataSerializer.create(ProtocolVersion.getLatest());
		try {
			packet.b(serverdata);
			int id = EnumProtocol.PLAY.a(EnumProtocolDirection.CLIENTBOUND, packet);
			Class<? extends ClientBoundMiddlePacket<RecyclableCollection<? extends ClientboundPEPacket>>> packetTransformerClass = registry.get(id);
			if (packetTransformerClass != null) {
				ClientBoundMiddlePacket<RecyclableCollection<? extends ClientboundPEPacket>> packetTransformer = packetTransformerClass.newInstance();
				if (packetTransformer.needsPlayer()) {
					packetTransformer.setPlayer(ChannelUtils.getPlayer(networkManager).getBukkitEntity());
				}
				packetTransformer.readFromServerData(serverdata);
				packetTransformer.setLocalStorage(storage);
				packetTransformer.setSharedStorage(sharedstorage);
				packetTransformer.handle();
				return packetTransformer.toData(ProtocolVersion.MINECRAFT_PE);
			} else {
				return RecyclableEmptyList.get();
			}
		} finally {
			serverdata.release();
		}
	}

}
