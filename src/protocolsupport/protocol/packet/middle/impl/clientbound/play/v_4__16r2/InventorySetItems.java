package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.storage.netcache.InventoryTransactionCache;
import protocolsupport.protocol.typeremapper.window.WindowRemapper;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.ClientItems;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.ClientItemsArray;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.ClientItemsSingle;
import protocolsupport.protocol.types.NetworkItemStack;

public class InventorySetItems extends MiddleInventorySetItems implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7,
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2,
IClientboundMiddlePacketV13,
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2 {

	public InventorySetItems(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();
	protected final InventoryTransactionCache transactioncache = cache.getTransactionCache();

	@Override
	protected void write() {
		String locale = clientCache.getLocale();

		WindowRemapper remapper = windowId == WINDOW_ID_PLAYER_INVENTORY ? windowCache.getPlayerWindowRemapper() : windowCache.getOpenedWindowRemapper();
		ClientItems clientitems = remapper.toClientItems(windowId, items);
		for (ClientItemsArray itemsArray : clientitems.getItemsArrays()) {
			io.writeClientbound(create(version, locale, itemsArray.getWindowId(), itemsArray.getItems()));
		}
		for (ClientItemsSingle itemSingle : clientitems.getItemsSingle()) {
			io.writeClientbound(InventorySetSlot.create(
				version, locale,
				itemSingle.getWindowId(),
				itemSingle.getSlot(),
				itemSingle.getItem()
			));
		}
		io.writeClientbound(InventorySetSlot.create(version, locale, MiddleInventorySetSlot.WINDOW_ID_PLAYER_CURSOR, -1, cursor));
		io.writeClientbound(SyncPing.createTransaction(transactioncache.storeInventoryStateServerId(stateId, windowId == WINDOW_ID_PLAYER_INVENTORY)));
	}

	protected static ClientBoundPacketData create(ProtocolVersion version, String locale, byte windowId, NetworkItemStack[] items) {
		ClientBoundPacketData windowitems = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_SET_ITEMS);
		windowitems.writeByte(windowId);
		ArrayCodec.writeShortTArray(windowitems, items, (lTo, item) -> ItemStackCodec.writeItemStack(lTo, version, locale, item));
		return windowitems;
	}

}
