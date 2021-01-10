package protocolsupport.protocol.packet.middleimpl.serverbound.login.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.login.MiddleEncryptionResponse;
import protocolsupport.protocol.serializer.ArraySerializer;

public class EncryptionResponse extends MiddleEncryptionResponse {

	public EncryptionResponse(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		sharedSecret = ArraySerializer.readVarIntByteArraySlice(clientdata, 256);
		verifyToken = ArraySerializer.readVarIntByteArraySlice(clientdata, 256);
	}

}
