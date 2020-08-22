package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

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
	protected void writeToClient() {
		ClientBoundPacketData encryptionrequest = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LOGIN_ENCRYPTION_BEGIN);
		StringSerializer.writeVarIntUTF8String(encryptionrequest, serverId);
		ArraySerializer.writeVarIntByteArray(encryptionrequest, publicKey);
		ArraySerializer.writeVarIntByteArray(encryptionrequest, verifyToken);
		codec.write(encryptionrequest);
	}

}
