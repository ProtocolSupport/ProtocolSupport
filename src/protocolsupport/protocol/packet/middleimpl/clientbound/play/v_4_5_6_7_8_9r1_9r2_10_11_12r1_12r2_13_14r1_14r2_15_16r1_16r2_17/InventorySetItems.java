package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.window.WindowRemapper;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.ClientItems;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.ClientItemsArray;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.ClientItemsSingle;
import protocolsupport.protocol.types.NetworkItemStack;

public class InventorySetItems extends MiddleInventorySetItems {

	public InventorySetItems(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		String locale = clientCache.getLocale();

		WindowRemapper remapper = windowId == WINDOW_ID_PLAYER_INVENTORY ? windowCache.getPlayerWindowRemapper() : windowCache.getOpenedWindowRemapper();
		ClientItems clientitems = remapper.toClientItems(windowId, items);
		for (ClientItemsArray itemsArray : clientitems.getItemsArrays()) {
			codec.writeClientbound(create(version, locale, itemsArray.getWindowId(), itemsArray.getItems()));
		}
		for (ClientItemsSingle itemSingle : clientitems.getItemsSingle()) {
			codec.writeClientbound(InventorySetSlot.create(
				version, locale,
				itemSingle.getWindowId(),
				itemSingle.getSlot(),
				itemSingle.getItem()
			));
		}
	}

	protected static ClientBoundPacketData create(ProtocolVersion version, String locale, byte windowId, NetworkItemStack[] items) {
		ClientBoundPacketData windowitems = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_SET_ITEMS);
		windowitems.writeByte(windowId);
		ArraySerializer.writeShortTArray(windowitems, items, (lTo, item) -> ItemStackSerializer.writeItemStack(lTo, version, locale, item));
		return windowitems;
	}

}
