package protocolsupport.protocol.packet.middle.clientbound.login;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;

public abstract class MiddleLoginSuccess extends ClientBoundMiddlePacket {

	public MiddleLoginSuccess(ConnectionImpl connection) {
		super(connection);
	}

	protected UUID uuid;
	protected String name;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		uuid = UUIDSerializer.readUUID4I(serverdata);
		name = StringSerializer.readVarIntUTF8String(serverdata);
	}

	@Override
	protected void cleanup() {
		uuid = null;
		name = null;
	}

}
