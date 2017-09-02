package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryConfirmTransaction;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.zplatform.ServerPlatform;

public class InventoryConfirmTransaction extends MiddleInventoryConfirmTransaction {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		System.out.println("Faking apology to the server >.<");
		connection.receivePacket(ServerPlatform.get().getPacketFactory().createInboundInventoryConfirmTransactionPacket(windowId, actionNumber, accepted));
		return RecyclableEmptyList.get();
	}

}
