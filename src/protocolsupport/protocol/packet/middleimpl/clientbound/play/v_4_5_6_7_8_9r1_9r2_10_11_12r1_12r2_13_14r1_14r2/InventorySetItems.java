package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetItems;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.typeremapper.window.WindowRemapper;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.ClientItems;
import protocolsupport.protocol.types.NetworkItemStack;

public class InventorySetItems extends MiddleInventorySetItems {

	public InventorySetItems(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		WindowRemapper remapper = windowId == WINDOW_ID_PLAYER_INVENTORY ? windowCache.getPlayerWindowRemapper() : windowCache.getOpenedWindowRemapper();

		String locale = cache.getAttributesCache().getLocale();
		for (ClientItems windowitems : remapper.toClientItems(windowId, items)) {
			codec.write(create(version, locale, windowitems.getWindowId(), windowitems.getItems()));
		}
	}

	protected static ClientBoundPacketData create(ProtocolVersion version, String locale, byte windowId, NetworkItemStack[] items) {
		ClientBoundPacketData windowitems = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_SET_ITEMS);
		windowitems.writeByte(windowId);
		ArraySerializer.writeShortTArray(windowitems, items, (lTo, item) -> ItemStackSerializer.writeItemStack(lTo, version, locale, item));
		return windowitems;
	}

}
