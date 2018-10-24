package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventorySetSlot extends MiddleInventorySetSlot {

	public InventorySetSlot(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9) && ((cache.getWindowCache().getOpenedWindow() == WindowType.PLAYER) || (windowId == 0)) && (slot == 45)) {
			return RecyclableEmptyList.get();
		}
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9) && (cache.getWindowCache().getOpenedWindow() == WindowType.BREWING)) {
			if (slot == 4) {
				return RecyclableEmptyList.get();
			}
			if (slot > 4) {
				slot--;
			}
		} else if (version.isBefore(ProtocolVersion.MINECRAFT_1_8) && (cache.getWindowCache().getOpenedWindow() == WindowType.ENCHANT)) {
			if (slot == 1) {
				return RecyclableEmptyList.get();
			}
			if (slot > 1) {
				slot--;
			}
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID);
		serializer.writeByte(windowId);
		serializer.writeShort(slot);
		ItemStackSerializer.writeItemStack(serializer, version, cache.getAttributesCache().getLocale(), itemstack, true);
		return RecyclableSingletonList.create(serializer);
	}

}
