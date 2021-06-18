package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleEncryptionRequest;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class EncryptionRequest extends MiddleEncryptionRequest {

	public EncryptionRequest(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData encryptionrequest = ClientBoundPacketData.create(ClientBoundPacketType.LOGIN_ENCRYPTION_BEGIN);
		StringCodec.writeVarIntUTF8String(encryptionrequest, serverId);
		ArrayCodec.writeVarIntByteArray(encryptionrequest, publicKey);
		ArrayCodec.writeVarIntByteArray(encryptionrequest, verifyToken);
		codec.writeClientbound(encryptionrequest);
	}

}
