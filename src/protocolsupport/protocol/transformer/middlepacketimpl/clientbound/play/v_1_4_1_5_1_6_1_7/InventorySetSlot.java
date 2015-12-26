package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;

import org.bukkit.event.inventory.InventoryType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventorySetSlot extends MiddleInventorySetSlot<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (player.getOpenInventory().getType() == InventoryType.ENCHANTING) {
			if (slot == 1) {
				return RecyclableEmptyList.get();
			}
			if (slot > 0) {
				slot--;
			}
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, version);
		serializer.writeByte(windowId);
		serializer.writeShort(slot);
		serializer.writeItemStack(itemstack);
		return RecyclableSingletonList.create(serializer);
	}

}
