package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleEncryptionRequest extends ClientBoundMiddlePacket {

	public MiddleEncryptionRequest(ConnectionImpl connection) {
		super(connection);
	}

	protected String serverId;
	protected ByteBuf publicKey;
	protected ByteBuf verifyToken;

	@Override
	public void readServerData(ByteBuf serverdata) {
		serverId = StringSerializer.readVarIntUTF8String(serverdata);
		publicKey = ArraySerializer.readVarIntByteArraySlice(serverdata);
		verifyToken = ArraySerializer.readVarIntByteArraySlice(serverdata);
	}

	@Override
	public void cleanup() {
		serverId = null;
		publicKey = null;
		verifyToken = null;
	}

}
