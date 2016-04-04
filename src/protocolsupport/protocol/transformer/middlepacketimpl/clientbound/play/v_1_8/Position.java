package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddlePosition;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
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
