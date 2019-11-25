package protocolsupport.protocol.packet.middle.serverbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;

public abstract class MiddleEncryptionResponse extends ServerBoundMiddlePacket {

	public MiddleEncryptionResponse(ConnectionImpl connection) {
		super(connection);
	}

	protected ByteBuf sharedSecret;
	protected ByteBuf verifyToken;

	@Override
	public void writeToServer() {
		ServerBoundPacketData encryptionresponse = ServerBoundPacketData.create(PacketType.SERVERBOUND_LOGIN_ENCRYPTION_BEGIN);
		ArraySerializer.writeVarIntByteArray(encryptionresponse, sharedSecret);
		ArraySerializer.writeVarIntByteArray(encryptionresponse, verifyToken);
		codec.read(encryptionresponse);
	}

}
