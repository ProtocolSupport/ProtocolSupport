package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddlePlayerListHeaderFooter extends ClientBoundMiddlePacket {

	protected String headerJson;
	protected String footerJson;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		headerJson = serializer.readString();
		footerJson = serializer.readString();
	}

}
