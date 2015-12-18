package protocolsupport.protocol.transformer.middlepacketimpl.serverbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;

import org.bukkit.event.inventory.InventoryType;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleInventoryClick;

public class InventoryClick extends MiddleInventoryClick {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		windowId = serializer.readUnsignedByte();
		slot = serializer.readShort();
		if (player.getOpenInventory().getType() == InventoryType.ENCHANTING) {
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
