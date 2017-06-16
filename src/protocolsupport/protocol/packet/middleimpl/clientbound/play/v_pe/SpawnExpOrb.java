package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnExpOrb;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnExpOrb extends MiddleSpawnExpOrb {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		// TODO: When metadata is implemented, set the exp count in the exp orb metadata!
		return RecyclableSingletonList.create(SpawnGlobal.create(version, entityId, x, y, z, 69));
	}

}
