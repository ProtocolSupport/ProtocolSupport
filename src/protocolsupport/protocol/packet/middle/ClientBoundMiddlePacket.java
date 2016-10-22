package protocolsupport.protocol.packet.middle;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class ClientBoundMiddlePacket<T> extends MiddlePacket {

	public void handle() {
	}

	public abstract void readFromServerData(ProtocolSupportPacketDataSerializer serializer);

	public abstract T toData(ProtocolVersion version);

}
