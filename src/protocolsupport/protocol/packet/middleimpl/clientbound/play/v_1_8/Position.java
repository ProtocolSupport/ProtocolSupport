package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePosition;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Position extends MiddlePosition<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_POSITION_ID, version);
		if (teleportConfirmId == 0) {
			serializer.writeDouble(xOrig);
			serializer.writeDouble(yOrig);
			serializer.writeDouble(zOrig);
			serializer.writeFloat(yawOrig);
			serializer.writeFloat(pitchOrig);
			serializer.writeByte(flags);
		} else {
			serializer.writeDouble(x);
			serializer.writeDouble(y);
			serializer.writeDouble(z);
			serializer.writeFloat(yaw);
			serializer.writeFloat(pitch);
			serializer.writeByte(0);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
