package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnGlobal;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class SpawnGlobal extends MiddleSpawnGlobal {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		//TODO: implement after global entity is added to main branch
		//return RecyclableSingletonList.create(SpawnLiving.createSimple(connection.getVersion(), cache.getAttributesCache().getLocale(), entityId, x, y, z, 93));
		return RecyclableEmptyList.get();
	}

}
