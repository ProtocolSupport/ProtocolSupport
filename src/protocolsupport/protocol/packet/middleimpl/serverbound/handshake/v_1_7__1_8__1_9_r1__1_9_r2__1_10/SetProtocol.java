package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_1_7__1_8__1_9_r1__1_9_r2__1_10;

import protocolsupport.protocol.packet.middle.serverbound.handshake.MiddleSetProtocol;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class SetProtocol extends MiddleSetProtocol {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		serializer.readVarInt();
		hostname = serializer.readString();
		port = serializer.readUnsignedShort();
		nextState = serializer.readVarInt();
	}

}
