package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryOpen extends MiddleInventoryOpen<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		int id = LegacyUtils.getInventoryId(invname);
		if (IdSkipper.INVENTORY.getTable(version).shouldSkip(id)) {
			player.closeInventory();
			return RecyclableEmptyList.get();
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WINDOW_OPEN_ID, version);
		serializer.writeByte(windowId);
		serializer.writeByte(id);
		serializer.writeString(LegacyUtils.toText(ChatSerializer.a(titleJson)));
		serializer.writeByte(slots);
		return RecyclableSingletonList.create(serializer);
	}

}
