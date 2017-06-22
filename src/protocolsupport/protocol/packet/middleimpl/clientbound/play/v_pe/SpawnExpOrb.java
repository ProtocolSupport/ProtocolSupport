package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnExpOrb;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnExpOrb extends MiddleSpawnExpOrb {

	public static int typeIdExpOrb = 93;
	
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		// TODO: When metadata is implemented, set the exp count in the exp orb metadata!
		return RecyclableSingletonList.create(SpawnLiving.createSimple(version, entityId, x, y, z, typeIdExpOrb));
	}

}
