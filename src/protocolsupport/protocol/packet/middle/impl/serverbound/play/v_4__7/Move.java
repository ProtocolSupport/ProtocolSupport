package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_4__7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleMove;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;

public class Move extends MiddleMove implements
IServerboundMiddlePacketV4,
IServerboundMiddlePacketV5,
IServerboundMiddlePacketV6,
IServerboundMiddlePacketV7 {

	public Move(IMiddlePacketInit init) {
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
