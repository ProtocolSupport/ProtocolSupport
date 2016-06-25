package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_4__1_5__1_6__1_7;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateSign;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class UpdateSign extends MiddleUpdateSign {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		position = serializer.readLegacyPositionS();
		for (int i = 0; i < 4; i++) {
			lines[i] = serializer.readString(15);
		}
	}

}
