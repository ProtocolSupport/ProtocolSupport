package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.inventory.fakes.PEFakeContainer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryClose extends ServerBoundMiddlePacket {

	protected int windowId;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		windowId = clientdata.readByte();
		if (windowId == -1) {
			windowId = cache.getWindowCache().getOpenedWindowId(); //Some inventories are a mess :F
		}
		cache.getPEInventoryCache().setPreviousWindowId(0);
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		//Apparently PE sends close packets if a new window is opened, we don't want the server or client closing that new window :F
		if (windowId == cache.getWindowCache().getOpenedWindowId()) {
			cache.getPEInventoryCache().getTransactionRemapper().clear();
			cache.getWindowCache().closeWindow();
			PEFakeContainer.destroyContainers(connection, cache);
			return RecyclableSingletonList.create(MiddleInventoryClose.create(windowId));
		}
		return RecyclableEmptyList.get();
	}

}
