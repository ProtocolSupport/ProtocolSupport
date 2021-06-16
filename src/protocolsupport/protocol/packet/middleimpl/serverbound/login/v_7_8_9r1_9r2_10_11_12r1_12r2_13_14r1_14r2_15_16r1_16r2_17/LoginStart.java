package protocolsupport.protocol.packet.middleimpl.serverbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.login.MiddleLoginStart;
import protocolsupport.protocol.serializer.StringSerializer;

public class LoginStart extends MiddleLoginStart {

	public LoginStart(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		name = StringSerializer.readVarIntUTF8String(clientdata, 16);
	}

}
