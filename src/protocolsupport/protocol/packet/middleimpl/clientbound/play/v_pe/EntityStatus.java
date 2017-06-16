package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityStatus;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityStatus extends MiddleEntityStatus {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_EVENT, version);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		// TODO: Update status codes when added.
		// Check, but so far no tested unknown status codes break PE.
		switch (cache.getWatchedEntity(entityId).getType()) {
			case FISHING_FLOAT: {
				if (status == 31) {
					status = 13;
				}
			}
			default: {
				break;
			}
		}
		serializer.writeByte((byte) status);
		VarNumberSerializer.writeVarInt(serializer, 0);
		return RecyclableSingletonList.create(serializer);
	}

}
