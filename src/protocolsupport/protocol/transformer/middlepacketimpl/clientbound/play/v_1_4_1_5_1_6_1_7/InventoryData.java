package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.bukkit.event.inventory.InventoryType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleInventoryData;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class InventoryData extends MiddleInventoryData<Collection<PacketData>> {

	private static final int[] furTypeTr = {1, 2, 0};

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (player.getOpenInventory().getType() == InventoryType.FURNACE) {
			if (type < furTypeTr.length) {
				type = furTypeTr[type];
			}
		}
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeByte(windowId);
		serializer.writeShort(type);
		serializer.writeShort(value);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_WINDOW_DATA_ID, serializer));
	}

}
