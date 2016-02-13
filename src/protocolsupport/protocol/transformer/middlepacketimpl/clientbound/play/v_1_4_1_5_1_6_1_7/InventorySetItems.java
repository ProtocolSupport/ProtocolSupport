package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;

import org.bukkit.event.inventory.InventoryType;

import net.minecraft.server.v1_8_R3.ItemStack;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventorySetItems extends MiddleInventorySetItems<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (player.getOpenInventory().getType() == InventoryType.ENCHANTING) {
			itemstacks.remove(1);
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, version);
		serializer.writeByte(windowId);
		serializer.writeShort(itemstacks.size());
		for (ItemStack itemstack : itemstacks) {
			serializer.writeItemStack(itemstack);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
