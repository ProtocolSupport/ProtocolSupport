package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddlePlayerListHeaderFooter extends ClientBoundMiddlePacket {

	protected String headerJson;
	protected String footerJson;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		headerJson = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
		footerJson = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
	}

}
