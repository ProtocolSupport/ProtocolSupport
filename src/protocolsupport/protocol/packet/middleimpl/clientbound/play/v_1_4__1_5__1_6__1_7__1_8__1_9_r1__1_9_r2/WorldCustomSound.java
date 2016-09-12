package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldCustomSound extends MiddleWorldCustomSound<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WORLD_SOUND_ID, version);
		serializer.writeString(id);
		serializer.writeInt(x);
		serializer.writeInt(y);
		serializer.writeInt(z);
		serializer.writeFloat(volume);
		serializer.writeByte((int) (pitch * 63.5));
		return RecyclableSingletonList.create(serializer);
	}

}
