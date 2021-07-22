package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17r1;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.NoSuchSlotException;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.WindowSlot;
import protocolsupport.protocol.types.NetworkItemStack;

public class InventorySetSlot extends MiddleInventorySetSlot {

	public InventorySetSlot(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		String locale = clientCache.getLocale();

		if (windowId == WINDOW_ID_PLAYER_CURSOR) {
			codec.writeClientbound(create(version, locale, windowId, slot, itemstack));
			return;
		}

		if (windowId == WINDOW_ID_PLAYER_INVENTORY) {
			codec.writeClientbound(create(version, locale, windowId, slot, itemstack));
			return;
		}

		if (windowId == WINDOW_ID_PLAYER_HOTBAR) {
			codec.writeClientbound(create(version, locale, windowId, slot, itemstack));
			return;
		}

		try {
			WindowSlot windowSlot = windowCache.getOpenedWindowRemapper().toClientSlot(windowId, slot);
			codec.writeClientbound(create(version, locale, windowSlot.getWindowId(), windowSlot.getSlot(), itemstack));
		} catch (NoSuchSlotException e) {
		}
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String locale, byte windowId, int slot, NetworkItemStack itemstack) {
		ClientBoundPacketData windowslot = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_SET_SLOT);
		windowslot.writeByte(windowId);
		windowslot.writeShort(slot);
		ItemStackCodec.writeItemStack(windowslot, version, locale, itemstack);
		return windowslot;
	}

}
