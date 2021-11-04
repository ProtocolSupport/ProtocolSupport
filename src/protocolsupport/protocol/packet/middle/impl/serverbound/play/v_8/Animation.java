package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleAnimation;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV8;
import protocolsupport.protocol.types.UsedHand;

public class Animation extends MiddleAnimation implements IServerboundMiddlePacketV8 {

	public Animation(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		hand = UsedHand.MAIN;
	}

}
