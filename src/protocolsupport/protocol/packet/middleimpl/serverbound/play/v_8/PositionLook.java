package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8.AbstractMoveLook;

public class PositionLook extends AbstractMoveLook {

	public PositionLook(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		x = clientdata.readDouble();
		y = clientdata.readDouble();
		z = clientdata.readDouble();
		yaw = clientdata.readFloat();
		pitch = clientdata.readFloat();
		onGround = clientdata.readBoolean();
	}

}
