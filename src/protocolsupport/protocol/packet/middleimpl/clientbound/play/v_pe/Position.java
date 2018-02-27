package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.util.NumberConversions;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class Position extends MiddlePosition {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		int chunkX = NumberConversions.floor(x) >> 4;
		int chunkZ = NumberConversions.floor(z) >> 4;
		if (!cache.getPEDataCache().getChunkCache().isChunkMarkedAsSent(chunkX, chunkZ)) {
			packets.add(Chunk.createEmptyChunk(version, chunkX, chunkZ));
		}
		if(cache.shouldResendPEClientPosition()) {
			packets.add(create(version, cache.getWatchedSelf(), x, y + 0.01, z, pitch, yaw, ANIMATION_MODE_TELEPORT));
		}
		return packets;
	}

	@Override
	public boolean postFromServerRead() {
		if (teleportConfirmId != 0) {
			cache.setTeleportLocation(x, y, z, teleportConfirmId);
		}
		return true;
	}

	public static final int ANIMATION_MODE_ALL = 0;
	public static final int ANIMATION_MODE_TELEPORT = 2;

	public static ClientBoundPacketData create(ProtocolVersion version, NetworkEntity entity, double x, double y, double z, float pitch, float yaw, int mode) {
		y = y + 1.6200000047683716D;
		float realYaw = (float) (yaw * (360D/256D));
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAYER_MOVE, version);
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		serializer.writeFloatLE((float) x);
		serializer.writeFloatLE((float) y);
		serializer.writeFloatLE((float) z);
		serializer.writeFloatLE(pitch);
		serializer.writeFloatLE(realYaw);
		serializer.writeFloatLE(realYaw); //head yaw actually
		serializer.writeByte(mode);
		serializer.writeBoolean(false); //on ground
		VarNumberSerializer.writeVarLong(serializer, entity.getDataCache().getVehicleId());
		return serializer;
	}

}
