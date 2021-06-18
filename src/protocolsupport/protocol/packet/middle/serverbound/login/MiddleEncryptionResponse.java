package protocolsupport.protocol.packet.middle.serverbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleEncryptionResponse extends ServerBoundMiddlePacket {

	protected MiddleEncryptionResponse(MiddlePacketInit init) {
		super(init);
	}

	protected ByteBuf sharedSecret;
	protected ByteBuf verifyToken;

	@Override
	protected void write() {
		ServerBoundPacketData encryptionresponse = ServerBoundPacketData.create(ServerBoundPacketType.LOGIN_ENCRYPTION_BEGIN);
		ArrayCodec.writeVarIntByteArray(encryptionresponse, sharedSecret);
		ArrayCodec.writeVarIntByteArray(encryptionresponse, verifyToken);
		codec.writeServerbound(encryptionresponse);
	}

}
