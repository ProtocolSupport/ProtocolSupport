package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleEncryptionRequest extends ClientBoundMiddlePacket {

	protected String serverId;
	protected byte[] publicKey;
	protected byte[] verifyToken;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		serverId = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(ProtocolType.PC));
		publicKey = ArraySerializer.readByteArray(serverdata, ProtocolVersion.getLatest(ProtocolType.PC));
		verifyToken = ArraySerializer.readByteArray(serverdata, ProtocolVersion.getLatest(ProtocolType.PC));
	}

}
