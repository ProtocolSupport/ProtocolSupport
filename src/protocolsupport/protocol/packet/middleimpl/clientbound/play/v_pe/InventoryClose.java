package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PEInventory;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryClose extends MiddleInventoryClose {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		cache.getPEInventoryCache().getTransactionRemapper().clear();
		PEInventory.destroyFakeContainers(connection, cache);
		return RecyclableSingletonList.create(create(connection.getVersion(), windowId));
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, int windowId) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CONTAINER_CLOSE, version);
		serializer.writeByte(windowId);
		return serializer;
	}

}
