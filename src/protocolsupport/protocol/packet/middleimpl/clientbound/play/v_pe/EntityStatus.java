package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.util.Arrays;
import java.util.List;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityStatus;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityStatus extends MiddleEntityStatus {

	List<Integer> allowedIds = Arrays.asList(2, 3, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 31, 34, 57, 63);
	
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if(allowedIds.contains(status)) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_EVENT, connection.getVersion());
			VarNumberSerializer.writeVarLong(serializer, entityId);
			// TODO: Update status codes when added. Remap status codes.
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
		return RecyclableEmptyList.get();
	}

}
