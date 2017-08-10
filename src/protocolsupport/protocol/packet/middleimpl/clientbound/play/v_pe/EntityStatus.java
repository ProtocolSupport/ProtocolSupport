package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import gnu.trove.set.hash.TIntHashSet;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityStatus;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityStatus extends MiddleEntityStatus {

	//TODO: Actually remap and skip the status codes. It seems that with the new update PE crashes if ID is unknown.
	TIntHashSet allowedIds = new TIntHashSet(new int[] {2, 3, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 31, 34, 57, 63});

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if(allowedIds.contains(status)) {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_EVENT, connection.getVersion());
			VarNumberSerializer.writeVarLong(serializer, entityId);
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
