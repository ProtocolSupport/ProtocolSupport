package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.utils.types.WindowType;

public class InventoryClick extends MiddleInventoryClick {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		windowId = serializer.readUnsignedByte();
		slot = serializer.readShort();
		if (serializer.getVersion().isBefore(ProtocolVersion.MINECRAFT_1_9) && (cache.getOpenedWindow() == WindowType.BREING)) {
			if (slot > 3) {
				slot++;
			}
		} else if (serializer.getVersion().isBefore(ProtocolVersion.MINECRAFT_1_8) && (cache.getOpenedWindow() == WindowType.ENCHANT)) {
			if (slot > 0) {
				slot++;
			}
		}
		button = serializer.readUnsignedByte();
		actionNumber = serializer.readShort();
		mode = serializer.readUnsignedByte();
		itemstack = serializer.readItemStack();
	}

}
