package protocolsupport.protocol.packet.middle.clientbound.login;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEncryptionRequest extends ClientBoundMiddlePacket {

	protected String serverId;
	protected byte[] publicKey;
	protected byte[] verifyToken;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		serverId = serializer.readString();
		publicKey = serializer.readByteArray();
		verifyToken = serializer.readByteArray();
	}

}
