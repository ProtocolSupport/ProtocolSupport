package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;

import org.bukkit.event.inventory.InventoryType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleInventoryData;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryData extends MiddleInventoryData<RecyclableCollection<PacketData>> {

	private static final int[] furTypeTr = {1, 2, 0};

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (player.getOpenInventory().getType() == InventoryType.FURNACE) {
			if (type < furTypeTr.length) {
				type = furTypeTr[type];
			}
		}
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WINDOW_DATA_ID, version);
		serializer.writeByte(windowId);
		serializer.writeShort(type);
		serializer.writeShort(value);
		return RecyclableSingletonList.<PacketData>create(serializer);
	}

}
