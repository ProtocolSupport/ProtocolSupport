package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8__1_9_r1__1_9_r2;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryOpen extends MiddleInventoryOpen<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WINDOW_OPEN_ID, version);
		serializer.writeByte(windowId);
		serializer.writeString(invname);
		serializer.writeString(titleJson);
		serializer.writeByte(slots);
		if (invname.equals("EntityHorse")) {
			serializer.writeInt(horseId);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
