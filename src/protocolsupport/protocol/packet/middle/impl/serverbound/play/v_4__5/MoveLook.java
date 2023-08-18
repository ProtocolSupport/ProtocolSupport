package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__5;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleLook;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__8.AbstractMoveLook;

public class MoveLook extends AbstractMoveLook implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5 {

	public MoveLook(IMiddlePacketInit init) {
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
			io.writeServerbound(MiddleLook.create(yaw, pitch, onGround));
		} else {
			super.write();
		}
	}

}
