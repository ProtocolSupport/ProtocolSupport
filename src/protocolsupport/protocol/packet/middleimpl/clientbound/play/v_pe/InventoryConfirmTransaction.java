package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryConfirmTransaction;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.zplatform.ServerPlatform;

public class InventoryConfirmTransaction extends MiddleInventoryConfirmTransaction {

	public InventoryConfirmTransaction(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		connection.receivePacket(ServerPlatform.get().getPacketFactory().createInboundInventoryConfirmTransactionPacket(windowId, actionNumber, accepted));
		return RecyclableEmptyList.get();
	}

}
