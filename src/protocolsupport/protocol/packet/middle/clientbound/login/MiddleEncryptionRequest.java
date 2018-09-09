package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleEncryptionRequest extends ClientBoundMiddlePacket {

	public MiddleEncryptionRequest(ConnectionImpl connection) {
		super(connection);
	}

	protected String serverId;
	protected ByteBuf publicKey;
	protected ByteBuf verifyToken;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		serverId = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
		publicKey = ArraySerializer.readVarIntByteArraySlice(serverdata);
		verifyToken = ArraySerializer.readVarIntByteArraySlice(serverdata);
	}

}
