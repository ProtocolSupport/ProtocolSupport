package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_4_5_6_7;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleEncryptionRequest;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class EncryptionRequest extends MiddleEncryptionRequest {

	public EncryptionRequest(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData encryptionrequest = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LOGIN_ENCRYPTION_BEGIN);
		StringSerializer.writeString(encryptionrequest, version, serverId);
		ArraySerializer.writeShortByteArray(encryptionrequest, publicKey);
		ArraySerializer.writeShortByteArray(encryptionrequest, verifyToken);
		codec.writeClientbound(encryptionrequest);
	}

}
