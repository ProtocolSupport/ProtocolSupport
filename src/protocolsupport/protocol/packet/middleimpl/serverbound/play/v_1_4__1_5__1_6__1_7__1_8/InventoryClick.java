package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7__1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.storage.SharedStorage.WindowType;

public class InventoryClick extends MiddleInventoryClick {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		windowId = serializer.readUnsignedByte();
		slot = serializer.readShort();
		if (serializer.getVersion().isBefore(ProtocolVersion.MINECRAFT_1_9) && sharedstorage.getOpenedWindow() == WindowType.BREING) {
			if (slot > 3) {
				slot++;
			}
		} else if (serializer.getVersion().isBefore(ProtocolVersion.MINECRAFT_1_8) && sharedstorage.getOpenedWindow() == WindowType.ENCHANT) {
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
