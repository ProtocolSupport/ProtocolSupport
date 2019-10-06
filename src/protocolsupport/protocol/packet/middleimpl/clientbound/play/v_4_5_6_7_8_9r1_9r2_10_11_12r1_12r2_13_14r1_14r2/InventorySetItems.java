package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.typeremapper.window.WindowRemapper;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.ClientItems;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventorySetItems extends MiddleInventorySetItems {

	public InventorySetItems(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		WindowRemapper remapper = windowId == WINDOW_ID_PLAYER_INVENTORY ? windowCache.getPlayerWindowRemapper() : windowCache.getOpenedWindowRemapper();

		ClientItems[] windowitemsarray = remapper.toClientItems(windowId, items);
		if (windowitemsarray.length == 1) {
			ClientItems windowitems = windowitemsarray[0];
			return RecyclableSingletonList.create(create(version, cache.getAttributesCache().getLocale(), windowitems.getWindowId(), windowitems.getItems()));
		} else {
			String locale = cache.getAttributesCache().getLocale();
			RecyclableArrayList<IPacketData> packets = RecyclableArrayList.create();
			for (ClientItems windowitems : windowitemsarray) {
				packets.add(create(version, locale, windowitems.getWindowId(), windowitems.getItems()));
			}
			return packets;
		}
	}

	protected static ClientBoundPacketData create(ProtocolVersion version, String locale, byte windowId, NetworkItemStack[] items) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_SET_ITEMS);
		serializer.writeByte(windowId);
		ArraySerializer.writeShortTArray(serializer, items, (lTo, item) -> ItemStackSerializer.writeItemStack(lTo, version, locale, item));
		return serializer;
	}

}
