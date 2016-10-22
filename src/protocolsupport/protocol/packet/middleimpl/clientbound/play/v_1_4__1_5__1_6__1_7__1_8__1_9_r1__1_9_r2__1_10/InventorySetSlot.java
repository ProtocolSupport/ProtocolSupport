package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventorySetSlot extends MiddleInventorySetSlot<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9) && ((cache.getOpenedWindow() == WindowType.PLAYER) || (windowId == 0)) && (slot == 45)) {
			return RecyclableEmptyList.get();
		}
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9) && (cache.getOpenedWindow() == WindowType.BREING)) {
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
		serializer.writeItemStack(itemstack);
		return RecyclableSingletonList.create(serializer);
	}

}
