package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class InventorySetItems extends MiddleInventorySetItems {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9) && ((cache.getOpenedWindow() == WindowType.PLAYER) || (windowId == 0))) {
			itemstacks.remove(itemstacks.size() - 1);
		}
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9) && (cache.getOpenedWindow() == WindowType.BREWING)) {
			itemstacks.remove(4);
		} else if (version.isBefore(ProtocolVersion.MINECRAFT_1_8) && (cache.getOpenedWindow() == WindowType.ENCHANT)) {
			itemstacks.remove(1);
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, version);
		serializer.writeByte(windowId);
		serializer.writeShort(itemstacks.size());
		for (ItemStackWrapper itemstack : itemstacks) {
			ItemStackSerializer.writeItemStack(serializer, version, itemstack, true);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
