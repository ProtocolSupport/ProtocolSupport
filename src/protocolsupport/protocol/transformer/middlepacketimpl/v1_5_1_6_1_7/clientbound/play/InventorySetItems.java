package protocolsupport.protocol.transformer.middlepacketimpl.v1_5_1_6_1_7.clientbound.play;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.bukkit.event.inventory.InventoryType;

import net.minecraft.server.v1_8_R3.ItemStack;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class InventorySetItems extends MiddleInventorySetItems<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (player.getOpenInventory().getType() == InventoryType.ENCHANTING) {
			itemstacks.remove(1);
		}
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeByte(windowId);
		serializer.writeShort(itemstacks.size());
		for (ItemStack itemstack : itemstacks) {
			serializer.writeItemStack(itemstack);
		}
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, serializer));
	}

}
