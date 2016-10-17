package protocolsupport.protocol.packet.middle;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class ClientBoundMiddlePacket<T> extends MiddlePacket {

	public void handle() {
	}

	public abstract void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException;

	public abstract T toData(ProtocolVersion version) throws IOException;

}
