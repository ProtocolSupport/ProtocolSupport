package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.Bukkit;
import org.bukkit.util.NumberConversions;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetPosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.MovementCache;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.types.ChunkCoord;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class SetPosition extends MiddleSetPosition {

	public SetPosition(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		MovementCache movecache = cache.getMovementCache();
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ChunkCoord chunk = new ChunkCoord(NumberConversions.floor(x) >> 4, NumberConversions.floor(z) >> 4);
		if (!cache.getPEChunkMapCache().isMarkedAsSent(chunk)) {
			packets.add(Chunk.createEmptyChunk(version, chunk));
		}
		//PE sends position that intersects blocks bounding boxes in some cases
		//Server doesn't accept such movements and will send a set position, but we ignore it unless it is above leniency
		if (movecache.isPEPositionAboveLeniency()) {
			packets.add(create(cache.getWatchedEntityCache().getSelfPlayer(), x, y + 0.01, z, pitch, yaw, ANIMATION_MODE_TELEPORT));
		}
		movecache.setPEClientPosition(x, y, z);
		//TODO: this might have a better home somewhere- but client must be pos-dim-switch-ack, and PSPE must know the new player location
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_8)) {
			packets.add(Chunk.createChunkPublisherUpdate((int) x, (int) y, (int) z));
		}
		return packets;
	}

	@Override
	public boolean postFromServerRead() {
		if (teleportConfirmId != 0) {
			cache.getMovementCache().setTeleportLocation(x, y, z, teleportConfirmId);
		}
		return true;
	}

	public static final int ANIMATION_MODE_ALL = 0;
	public static final int ANIMATION_MODE_TELEPORT = 2;

	public static ClientBoundPacketData create(NetworkEntity entity, double x, double y, double z, float pitch, float yaw, int mode) {
		y = y + 1.6200000047683716D;
		float headYaw = (float) (yaw * (360D / 256D));
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_MOVE);
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		serializer.writeFloatLE((float) x);
		serializer.writeFloatLE((float) y);
		serializer.writeFloatLE((float) z);
		serializer.writeFloatLE(pitch);
		serializer.writeFloatLE(yaw);
		serializer.writeFloatLE(headYaw);
		serializer.writeByte(mode);
		serializer.writeBoolean(false); //on ground
		VarNumberSerializer.writeVarLong(serializer, entity.getDataCache().getVehicleId());
		if (mode == ANIMATION_MODE_TELEPORT) {
			serializer.writeIntLE(0); //teleportCause
			serializer.writeIntLE(0); //teleportItem
		}
		return serializer;
	}

}
