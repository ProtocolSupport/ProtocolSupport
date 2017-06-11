package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public abstract class MiddleEncryptionRequest extends ClientBoundMiddlePacket {

	protected String serverId;
	protected byte[] publicKey;
	protected byte[] verifyToken;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		serverId = StringSerializer.readString(serverdata, ProtocolVersionsHelper.LATEST_PC);
		publicKey = ArraySerializer.readByteArray(serverdata, ProtocolVersionsHelper.LATEST_PC);
		verifyToken = ArraySerializer.readByteArray(serverdata, ProtocolVersionsHelper.LATEST_PC);
	}

}
