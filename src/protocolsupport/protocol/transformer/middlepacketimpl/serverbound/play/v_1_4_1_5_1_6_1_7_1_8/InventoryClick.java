package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.storage.SharedStorage.WindowType;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleInventoryClick;

public class InventoryClick extends MiddleInventoryClick {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		windowId = serializer.readUnsignedByte();
		slot = serializer.readShort();
		if (serializer.getVersion().isBefore(ProtocolVersion.MINECRAFT_1_8) && sharedstorage.getOpenedWindow() == WindowType.ENCHANT) {
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
