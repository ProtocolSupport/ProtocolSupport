package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleMove;

public class Move extends MiddleMove {

	public Move(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		x = clientdata.readDouble();
		y = clientdata.readDouble();
		clientdata.readDouble();
		z = clientdata.readDouble();
		onGround = clientdata.readBoolean();
	}

}
