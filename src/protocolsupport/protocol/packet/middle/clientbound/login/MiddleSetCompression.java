package protocolsupport.protocol.packet.middle.clientbound.login;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleSetCompression<T> extends ClientBoundMiddlePacket<T> {

	protected int threshold;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		threshold = serializer.readVarInt();
	}

}
