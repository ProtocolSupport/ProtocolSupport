package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.typeremapper.window.WindowRemapper;
import protocolsupport.protocol.typeremapper.window.WindowRemapper.SlotDoesntExistException;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventorySetSlot extends MiddleInventorySetSlot {

	public InventorySetSlot(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		String locale = cache.getAttributesCache().getLocale();
		if (windowId == WINDOW_ID_PLAYER_CURSOR) {
			return RecyclableSingletonList.create(create(version, locale, windowId, slot, itemstack));
		}
		if (windowId == WINDOW_ID_PLAYER_INVENTORY) {
			//TODO: remap for versions that don't actually support this special window id
			return RecyclableSingletonList.create(create(version, locale, windowId, slot, itemstack));
		}
		if ((windowId == WINDOW_ID_PLAYER_HOTBAR) && (slot >= 36) && (slot < 45)) {
			return RecyclableSingletonList.create(create(version, locale, windowId, slot, itemstack));
		}

		if (!cache.getWindowCache().isValidWindowId(windowId)) {
			return RecyclableEmptyList.get();
		}

		try {
			int windowSlot = windowCache.getOpenedWindowRemapper().toClientSlot(windowId, slot);
			return RecyclableSingletonList.create(create(
				version, locale, WindowRemapper.getClientSlotWindowId(windowSlot), WindowRemapper.getClientSlotSlot(windowSlot), itemstack
			));
		} catch (SlotDoesntExistException e) {
			return RecyclableEmptyList.get();
		}
	}

	protected static ClientBoundPacketData create(ProtocolVersion version, String locale, int windowId, int slot, NetworkItemStack itemstack) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_SET_SLOT);
		serializer.writeByte(windowId);
		serializer.writeShort(slot);
		ItemStackSerializer.writeItemStack(serializer, version, locale, itemstack);
		return serializer;
	}

}
