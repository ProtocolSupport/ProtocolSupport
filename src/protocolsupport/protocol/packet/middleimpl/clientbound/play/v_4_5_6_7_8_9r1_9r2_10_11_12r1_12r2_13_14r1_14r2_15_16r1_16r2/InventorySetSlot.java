package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.storage.netcache.InventoryTransactionCache;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.NoSuchSlotException;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.WindowSlot;
import protocolsupport.protocol.types.NetworkItemStack;

public class InventorySetSlot extends MiddleInventorySetSlot {

	public InventorySetSlot(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();
	protected final InventoryTransactionCache transactioncache = cache.getTransactionCache();

	@Override
	protected void write() {
		String locale = clientCache.getLocale();

		if (windowId == WINDOW_ID_PLAYER_CURSOR) {
			writeWithTansaction(create(version, locale, windowId, slot, itemstack));
			return;
		}

		if (windowId == WINDOW_ID_PLAYER_INVENTORY) {
			//TODO: remap for versions that don't actually support this special window id
			writeWithTansaction(create(version, locale, windowId, slot, itemstack));
			return;
		}

		if (windowId == WINDOW_ID_PLAYER_HOTBAR) {
			writeWithTansaction(create(version, locale, windowId, slot, itemstack));
			return;
		}

		try {
			WindowSlot windowSlot = windowCache.getOpenedWindowRemapper().toClientSlot(windowId, slot);
			writeWithTansaction(create(version, locale, windowSlot.getWindowId(), windowSlot.getSlot(), itemstack));
		} catch (NoSuchSlotException e) {
		}
	}

	protected void writeWithTansaction(ClientBoundPacketData packet) {
		codec.writeClientbound(packet);
		codec.writeClientbound(SyncPing.createTransaction(transactioncache.storeInvStateServerId(stateId)));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String locale, byte windowId, int slot, NetworkItemStack itemstack) {
		ClientBoundPacketData windowslot = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_SET_SLOT);
		windowslot.writeByte(windowId);
		windowslot.writeShort(slot);
		ItemStackCodec.writeItemStack(windowslot, version, locale, itemstack);
		return windowslot;
	}

}
