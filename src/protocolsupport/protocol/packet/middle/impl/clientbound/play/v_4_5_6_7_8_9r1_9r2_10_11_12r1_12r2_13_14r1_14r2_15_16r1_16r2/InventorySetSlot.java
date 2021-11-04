package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
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
import protocolsupport.protocol.typeremapper.window.WindowRemapper.NoSuchSlotException;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.WindowSlot;
import protocolsupport.protocol.types.NetworkItemStack;

public class InventorySetSlot extends MiddleInventorySetSlot implements
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

	public InventorySetSlot(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();
	protected final InventoryTransactionCache transactioncache = cache.getTransactionCache();

	@Override
	protected void write() {
		String locale = clientCache.getLocale();

		if (windowId == WINDOW_ID_PLAYER_CURSOR) {
			writeWithTansaction(create(version, locale, windowId, slot, itemstack), true);
			return;
		}

		if (windowId == WINDOW_ID_PLAYER_INVENTORY) {
			//TODO: remap for versions that don't actually support this special window id
			writeWithTansaction(create(version, locale, windowId, slot, itemstack), true);
			return;
		}

		if (windowId == WINDOW_ID_PLAYER_HOTBAR) {
			writeWithTansaction(create(version, locale, windowId, slot, itemstack), true);
			return;
		}

		try {
			WindowSlot windowSlot = windowCache.getOpenedWindowRemapper().toClientSlot(windowId, slot);
			writeWithTansaction(create(version, locale, windowSlot.getWindowId(), windowSlot.getSlot(), itemstack), false);
		} catch (NoSuchSlotException e) {
		}
	}

	protected void writeWithTansaction(ClientBoundPacketData packet, boolean player) {
		io.writeClientbound(packet);
		io.writeClientbound(SyncPing.createTransaction(transactioncache.storeInventoryStateServerId(stateId, player)));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String locale, byte windowId, int slot, NetworkItemStack itemstack) {
		ClientBoundPacketData windowslot = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_SET_SLOT);
		windowslot.writeByte(windowId);
		windowslot.writeShort(slot);
		ItemStackCodec.writeItemStack(windowslot, version, locale, itemstack);
		return windowslot;
	}

}
