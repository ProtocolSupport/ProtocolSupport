package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.storage.SharedStorage.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventorySetSlot extends MiddleInventorySetSlot<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9) && (sharedstorage.getOpenedWindow() == WindowType.PLAYER || windowId == 0) && slot == 45) {
			return RecyclableEmptyList.get();
		}
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_9) && sharedstorage.getOpenedWindow() == WindowType.BREING) {
			if (slot == 4) {
				return RecyclableEmptyList.get();
			}
			if (slot > 4) {
				slot--;
			}
		} else if (version.isBefore(ProtocolVersion.MINECRAFT_1_8) && sharedstorage.getOpenedWindow() == WindowType.ENCHANT) {
			if (slot == 1) {
				return RecyclableEmptyList.get();
			}
			if (slot > 1) {
				slot--;
			}
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WINDOW_SET_SLOT_ID, version);
		serializer.writeByte(windowId);
		serializer.writeShort(slot);
		serializer.writeItemStack(itemstack);
		return RecyclableSingletonList.create(serializer);
	}

}
