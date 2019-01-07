package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddlePlayerListHeaderFooter extends ClientBoundMiddlePacket {

	public MiddlePlayerListHeaderFooter(ConnectionImpl connection) {
		super(connection);
	}

	protected String headerJson;
	protected String footerJson;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		headerJson = StringSerializer.readVarIntUTF8String(serverdata);
		footerJson = StringSerializer.readVarIntUTF8String(serverdata);
	}

}
