package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClose;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.zplatform.ServerPlatform;

public class InventoryClose extends MiddleInventoryClose {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		System.out.println("CLIENT CLOSE!!!");
		windowId = clientdata.readByte();
		cache.getInfTransactions().clear();
		if(cache.getFakeInventoryCoords() != null) {
			destroyFakeInventory(cache.getFakeInventoryCoords());
		}
	}
	
	private void destroyFakeInventory(Position position) {
		System.out.println("Sending block updates.");
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createBlockUpdatePacket(position, 0));
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createBlockUpdatePacket(new Position(position.getX() + 1, 0, position.getZ()), 0));
	}

}
