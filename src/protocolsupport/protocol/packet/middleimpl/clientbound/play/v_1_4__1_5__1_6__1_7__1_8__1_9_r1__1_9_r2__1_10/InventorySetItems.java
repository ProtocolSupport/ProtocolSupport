package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.types.ItemStackWrapper;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventorySetItems extends MiddleInventorySetItems<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9) && ((cache.getOpenedWindow() == WindowType.PLAYER) || (windowId == 0))) {
			itemstacks.remove(itemstacks.size() - 1);
		}
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9) && (cache.getOpenedWindow() == WindowType.BREING)) {
			itemstacks.remove(4);
		} else if (version.isBefore(ProtocolVersion.MINECRAFT_1_8) && (cache.getOpenedWindow() == WindowType.ENCHANT)) {
			itemstacks.remove(1);
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_SET_ITEMS_ID, version);
		serializer.writeByte(windowId);
		serializer.writeShort(itemstacks.size());
		for (ItemStackWrapper itemstack : itemstacks) {
			serializer.writeItemStack(itemstack);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
