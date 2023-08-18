package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r2__20;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.window.WindowRemapper;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.ClientItems;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.ClientItemsArray;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.ClientItemsSingle;
import protocolsupport.protocol.types.NetworkItemStack;

public class InventorySetItems extends MiddleInventorySetItems implements
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18,
IClientboundMiddlePacketV20 {

	public InventorySetItems(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		String locale = clientCache.getLocale();

		WindowRemapper remapper = windowId == WINDOW_ID_PLAYER_INVENTORY ? windowCache.getPlayerWindowRemapper() : windowCache.getOpenedWindowRemapper();
		ClientItems clientitems = remapper.toClientItems(windowId, items);
		for (ClientItemsArray itemsArray : clientitems.getItemsArrays()) {
			io.writeClientbound(create(version, locale, itemsArray.getWindowId(), stateId, itemsArray.getItems(), cursor));
		}
		for (ClientItemsSingle itemSingle : clientitems.getItemsSingle()) {
			io.writeClientbound(InventorySetSlot.create(
				version, locale,
				itemSingle.getWindowId(),
				stateId,
				itemSingle.getSlot(),
				itemSingle.getItem()
			));
		}
	}

	protected static ClientBoundPacketData create(ProtocolVersion version, String locale, byte windowId, int stateId, NetworkItemStack[] items, NetworkItemStack cursor) {
		ClientBoundPacketData windowitems = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_SET_ITEMS);
		windowitems.writeByte(windowId);
		VarNumberCodec.writeVarInt(windowitems, stateId);
		ArrayCodec.writeVarIntTArray(windowitems, items, (lTo, item) -> ItemStackCodec.writeItemStack(lTo, version, locale, item));
		ItemStackCodec.writeItemStack(windowitems, version, cursor);
		return windowitems;
	}

}
