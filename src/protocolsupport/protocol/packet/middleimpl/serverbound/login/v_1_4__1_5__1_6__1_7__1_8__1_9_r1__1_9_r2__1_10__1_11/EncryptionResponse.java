package protocolsupport.protocol.packet.middleimpl.serverbound.login.v_1_4__1_5__1_6__1_7__1_8__1_9_r1__1_9_r2__1_10__1_11;

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
