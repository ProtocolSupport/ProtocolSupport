package protocolsupport.protocol.packet.middle.impl.serverbound.login.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.login.MiddleEncryptionResponse;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;

public class EncryptionResponse extends MiddleEncryptionResponse implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5,
IServerboundMiddlePacketV6,
IServerboundMiddlePacketV7 {

	public EncryptionResponse(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		sharedSecret = ArrayCodec.readShortByteArraySlice(clientdata, 256);
		verifyToken = ArrayCodec.readShortByteArraySlice(clientdata, 256);
	}

}
