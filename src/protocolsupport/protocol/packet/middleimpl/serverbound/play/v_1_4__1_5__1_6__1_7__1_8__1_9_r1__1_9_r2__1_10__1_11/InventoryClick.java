package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.types.WindowType;

public class InventoryClick extends MiddleInventoryClick {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		windowId = clientdata.readUnsignedByte();
		slot = clientdata.readShort();
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9) && (cache.getOpenedWindow() == WindowType.BREING)) {
			if (slot > 3) {
				slot++;
			}
		} else if (version.isBefore(ProtocolVersion.MINECRAFT_1_8) && (cache.getOpenedWindow() == WindowType.ENCHANT)) {
			if (slot > 0) {
				slot++;
			}
		}
		button = clientdata.readUnsignedByte();
		actionNumber = clientdata.readShort();
		mode = clientdata.readUnsignedByte();
		itemstack = ItemStackSerializer.readItemStack(clientdata, version);
	}

}
