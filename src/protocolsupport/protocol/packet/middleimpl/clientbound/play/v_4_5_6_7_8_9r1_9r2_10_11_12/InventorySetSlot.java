package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventorySetSlot extends MiddleInventorySetSlot {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9) && ((cache.getOpenedWindow() == WindowType.PLAYER) || (windowId == 0)) && (slot == 45)) {
			return RecyclableEmptyList.get();
		}
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9) && (cache.getOpenedWindow() == WindowType.BREWING)) {
			if (slot == 4) {
				return RecyclableEmptyList.get();
			}
			if (slot > 4) {
				slot--;
			}
		} else if (version.isBefore(ProtocolVersion.MINECRAFT_1_8) && (cache.getOpenedWindow() == WindowType.ENCHANT)) {
			if (slot == 1) {
				return RecyclableEmptyList.get();
			}
			if (slot > 1) {
				slot--;
			}
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, version);
		serializer.writeByte(windowId);
		serializer.writeShort(slot);
		ItemStackSerializer.writeItemStack(serializer, version, itemstack, true);
		return RecyclableSingletonList.create(serializer);
	}

}
