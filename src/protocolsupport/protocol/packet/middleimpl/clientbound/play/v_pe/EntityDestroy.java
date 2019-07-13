package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityDestroy;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class EntityDestroy extends MiddleEntityDestroy {

	public EntityDestroy(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntityDataCache dataCache = cache.getWatchedEntityCache().getSelfPlayer().getDataCache();
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		for (int entityId : entityIds) {
			if (dataCache.getVehicleId() == entityId) // If the user is riding something and the vehicle is destroyed, the unlink packet is never sent, causing desync (and errors) with the cache
				dataCache.setVehicleId(0);
			packets.add(create(entityId));
		}
		return packets;
	}

	public static ClientBoundPacketData create(long entityId) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_DESTROY);
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		return serializer;
	}

}
