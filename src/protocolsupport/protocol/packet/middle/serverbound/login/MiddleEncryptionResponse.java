package protocolsupport.protocol.packet.middle.serverbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;

public abstract class MiddleEncryptionResponse extends ServerBoundMiddlePacket {

	public MiddleEncryptionResponse(MiddlePacketInit init) {
		super(init);
	}

	protected ByteBuf sharedSecret;
	protected ByteBuf verifyToken;

	@Override
	protected void write() {
		ServerBoundPacketData encryptionresponse = ServerBoundPacketData.create(PacketType.SERVERBOUND_LOGIN_ENCRYPTION_BEGIN);
		ArraySerializer.writeVarIntByteArray(encryptionresponse, sharedSecret);
		ArraySerializer.writeVarIntByteArray(encryptionresponse, verifyToken);
		codec.writeServerbound(encryptionresponse);
	}

}
