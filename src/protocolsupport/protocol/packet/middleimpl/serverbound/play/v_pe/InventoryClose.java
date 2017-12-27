package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClose;
import protocolsupport.protocol.typeremapper.pe.PEInventory.InvBlock;
import protocolsupport.protocol.utils.types.GameMode;
import protocolsupport.zplatform.ServerPlatform;

public class InventoryClose extends MiddleInventoryClose {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		windowId = clientdata.readByte();
		cache.getInfTransactions().clear();
		if (cache.getGameMode() == GameMode.CREATIVE && windowId == -1) {
			windowId = 0;
		}
		if (connection.hasMetadata("peInvBlocks")) {
			destroyFakeContainers((InvBlock[]) connection.getMetadata("peInvBlocks"));
			connection.removeMetadata("peInvBlocks");
		}
	}
	
	private void destroyFakeContainers(InvBlock[] blocks) {
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createBlockUpdatePacket(
				blocks[0].getPosition(), blocks[0].getTypeData()));
		connection.sendPacket(ServerPlatform.get().getPacketFactory().createBlockUpdatePacket(
				blocks[1].getPosition(), blocks[1].getTypeData()));
	}

}
