package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleLoginSuccess extends ClientBoundMiddlePacket {

	public MiddleLoginSuccess(ConnectionImpl connection) {
		super(connection);
	}

	protected String uuidstring;
	protected String name;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		uuidstring = StringSerializer.readVarIntUTF8String(serverdata);
		name = StringSerializer.readVarIntUTF8String(serverdata);
	}

}
