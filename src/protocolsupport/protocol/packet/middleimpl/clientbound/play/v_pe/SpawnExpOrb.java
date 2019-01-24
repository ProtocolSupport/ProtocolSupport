package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnExpOrb;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnExpOrb extends MiddleSpawnExpOrb {

	public SpawnExpOrb(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		// TODO: When metadata is implemented, set the exp count in the exp orb metadata!
		return RecyclableSingletonList.create(SpawnLiving.createSimple(connection.getVersion(), cache.getAttributesCache().getLocale(), entity, (float) x, (float) y, (float) z));
	}

}
