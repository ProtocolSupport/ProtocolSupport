package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__8.AbstractMoveLook;

public class PositionLook extends AbstractMoveLook implements IServerboundMiddlePacketV8 {

	public PositionLook(IMiddlePacketInit init) {
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
