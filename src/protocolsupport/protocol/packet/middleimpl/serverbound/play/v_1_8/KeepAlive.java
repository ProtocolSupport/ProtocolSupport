package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleKeepAlive;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class KeepAlive extends MiddleKeepAlive {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		keepAliveId = serializer.readVarInt();
	}

}
