package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnGlobal;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnGlobal extends MiddleSpawnGlobal {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(SpawnLiving.create(connection.getVersion(), entityId, x, y, z, 0, 0, 0, 0, 0, null, 93));
	}
}
