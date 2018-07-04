package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityHeadRotation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.types.networkentity.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class EntityHeadRotation extends MiddleEntityHeadRotation {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity != null) {
			entity.getDataCache().setHeadRotation(headRot);
		}
		return RecyclableEmptyList.get();
	}

}
