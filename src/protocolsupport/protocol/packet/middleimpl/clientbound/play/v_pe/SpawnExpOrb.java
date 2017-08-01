package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnExpOrb;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnExpOrb extends MiddleSpawnExpOrb {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		// TODO: When metadata is implemented, set the exp count in the exp orb metadata!
		return RecyclableSingletonList.create(SpawnLiving.create(connection.getVersion(), entityId, x, y, z, 0, 0, 0, 0, 0, null, 69));
	}

}
