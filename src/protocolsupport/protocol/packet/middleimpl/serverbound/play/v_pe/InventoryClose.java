package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClose;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.typeremapper.pe.PEInventory.InvBlock;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.ServerPlatform;

public class InventoryClose extends ServerBoundMiddlePacket {

	protected byte windowId;
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		windowId = clientdata.readByte();
		System.out.println("CLIENT CLOSE" + windowId);
		System.out.println("CACHED ID: " + cache.getOpenedWindowId());
		if (cache.getGameMode() == GameMode.CREATIVE && windowId == -1) {
			windowId = 0;
		}

	}
	
	private void destroyFakeContainers(InvBlock[] blocks) {
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createBlockUpdatePacket(
				blocks[0].getPosition(), blocks[0].getTypeData()));
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createBlockUpdatePacket(
				blocks[1].getPosition(), blocks[1].getTypeData()));
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		//Apparently PE sends close packets if a new window is opened, we don't want the server or client closing that new window :F
		if (windowId == cache.getOpenedWindowId()) {
			cache.getInfTransactions().clear();
			System.out.println("SENDING CLOSE!");
			if (connection.hasMetadata("peInvBlocks")) {
				destroyFakeContainers((InvBlock[]) connection.getMetadata("peInvBlocks"));
				connection.removeMetadata("peInvBlocks");
			}
			cache.closeWindow();
			return RecyclableSingletonList.create(MiddleInventoryClose.create(windowId));
		}
		System.out.println("NOT SENDING CLOSE!");
		return RecyclableEmptyList.get();
	}

}
