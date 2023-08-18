package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r2__20;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.NoSuchSlotException;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.WindowSlot;
import protocolsupport.protocol.types.NetworkItemStack;

public class InventorySetSlot extends MiddleInventorySetSlot implements
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18,
IClientboundMiddlePacketV20 {

	public InventorySetSlot(IMiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void write() {
		String locale = clientCache.getLocale();

		if (windowId == WINDOW_ID_PLAYER_CURSOR) {
			io.writeClientbound(create(version, locale, windowId, stateId, slot, itemstack));
			return;
		}

		if (windowId == WINDOW_ID_PLAYER_INVENTORY) {
			io.writeClientbound(create(version, locale, windowId, stateId, slot, itemstack));
			return;
		}

		if (windowId == WINDOW_ID_PLAYER_HOTBAR) {
			io.writeClientbound(create(version, locale, windowId, stateId, slot, itemstack));
			return;
		}

		try {
			WindowSlot windowSlot = windowCache.getOpenedWindowRemapper().toClientSlot(windowId, slot);
			io.writeClientbound(create(version, locale, windowSlot.getWindowId(), stateId, windowSlot.getSlot(), itemstack));
		} catch (NoSuchSlotException e) {
		}
	}

	protected static ClientBoundPacketData create(ProtocolVersion version, String locale, byte windowId, int stateId, int slot, NetworkItemStack itemstack) {
		ClientBoundPacketData windowslot = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_SET_SLOT);
		windowslot.writeByte(windowId);
		VarNumberCodec.writeVarInt(windowslot, stateId);
		windowslot.writeShort(slot);
		ItemStackCodec.writeItemStack(windowslot, version, locale, itemstack);
		return windowslot;
	}

}
