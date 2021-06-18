package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_4_5_6_7;

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
		StringCodec.writeString(encryptionrequest, version, serverId);
		ArrayCodec.writeShortByteArray(encryptionrequest, publicKey);
		ArrayCodec.writeShortByteArray(encryptionrequest, verifyToken);
		codec.writeClientbound(encryptionrequest);
	}

}
