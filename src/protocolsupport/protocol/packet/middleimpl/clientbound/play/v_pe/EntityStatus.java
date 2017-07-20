package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityStatus;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.protocol.utils.types.NetworkEntity.DataCache;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class EntityStatus extends MiddleEntityStatus {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		NetworkEntity e = cache.getWatchedEntity(entityId);
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		// TODO: Update status codes when added.
		// Check, but so far no tested unknown status codes break PE.
		
		if(status == 31 && e.isOfType(NetworkEntityType.FISHING_FLOAT)) {
			status = 13;
		}
		if(status == 18 && e.isOfType(NetworkEntityType.TAMEABLE)) {
			DataCache data = cache.getWatchedEntity(entityId).getDataCache();
			data.inLove = true;
			cache.updateWatchedDataCache(entityId, data);
			//Update meta as well.
			packets.add(EntityMetadata.create(e, version));
		}
		
		packets.add(create(e, status, version));
		return packets;
	}
	
	public static int PE_UNLEASH = 63;
	
	public static ClientBoundPacketData create(NetworkEntity entity, int status, ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_EVENT, version);
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		serializer.writeByte((byte) status);
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		return serializer;
	}

}
