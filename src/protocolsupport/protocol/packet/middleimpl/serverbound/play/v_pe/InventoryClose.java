package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PEInventory;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryClose extends ServerBoundMiddlePacket {

	protected byte windowId;
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		windowId = clientdata.readByte();
		if (cache.getPEDataCache().getAttributesCache().getGameMode() == GameMode.CREATIVE && windowId == -1) {
			windowId = 0; //Destroying items or something like that. Well it should just be 0.
		}
	}
	
	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		//Apparently PE sends close packets if a new window is opened, we don't want the server or client closing that new window :F
		if (windowId == cache.getOpenedWindowId()) {
			cache.getPEDataCache().getInventoryCache().getInfTransactions().clear();
			cache.closeWindow();
			PEInventory.destroyFakeContainers(connection, cache);
			return RecyclableSingletonList.create(MiddleInventoryClose.create(windowId));
		}
		return RecyclableEmptyList.get();
	}

}
