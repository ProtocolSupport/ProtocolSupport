package protocolsupport.protocol.packet.middleimpl.serverbound.login.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.packet.middle.serverbound.login.MiddleEncryptionResponse;

public class EncryptionResponse extends MiddleEncryptionResponse {

	public EncryptionResponse(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		sharedSecret = ArrayCodec.readShortByteArraySlice(clientdata, 256);
		verifyToken = ArrayCodec.readShortByteArraySlice(clientdata, 256);
	}

}
