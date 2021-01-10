package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleLook;
import protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8.AbstractMoveLook;

public class MoveLook extends AbstractMoveLook {

	public MoveLook(MiddlePacketInit init) {
		super(init);
	}

	protected double yhead;

	@Override
	protected void read(ByteBuf clientdata) {
		x = clientdata.readDouble();
		y = clientdata.readDouble();
		yhead = clientdata.readDouble();
		z = clientdata.readDouble();
		yaw = clientdata.readFloat();
		pitch = clientdata.readFloat();
		onGround = clientdata.readBoolean();
	}

	@Override
	protected void write() {
		if ((y == -999.0D) && (yhead == -999.0D)) {
			codec.writeServerbound(MiddleLook.create(yaw, pitch, onGround));
		} else {
			super.write();
		}
	}

}
