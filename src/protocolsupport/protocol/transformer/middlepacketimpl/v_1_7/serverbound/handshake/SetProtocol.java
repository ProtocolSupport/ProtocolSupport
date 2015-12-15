package protocolsupport.protocol.transformer.middlepacketimpl.v_1_7.serverbound.handshake;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.handshake.MiddleSetProtocol;

public class SetProtocol extends MiddleSetProtocol {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		serializer.readVarInt();
		hostname = serializer.readString(Short.MAX_VALUE);
		port = serializer.readUnsignedShort();
		nextState = serializer.readVarInt();
	}

}
