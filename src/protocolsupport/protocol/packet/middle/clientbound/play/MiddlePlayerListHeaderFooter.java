package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddlePlayerListHeaderFooter<T> extends ClientBoundMiddlePacket<T> {

	protected String headerJson;
	protected String footerJson;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		headerJson = serializer.readString();
		footerJson = serializer.readString();
	}

}
