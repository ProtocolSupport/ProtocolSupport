package protocolsupport.protocol.packet.middleimpl.serverbound.login.v_4_5_6_7_8_9r1_9r2_10_11_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.login.MiddleEncryptionResponse;
import protocolsupport.protocol.serializer.ArraySerializer;

public class EncryptionResponse extends MiddleEncryptionResponse {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		sharedSecret = ArraySerializer.readByteArray(clientdata, version, 256);
		verifyToken = ArraySerializer.readByteArray(clientdata, version, 256);
	}

}
