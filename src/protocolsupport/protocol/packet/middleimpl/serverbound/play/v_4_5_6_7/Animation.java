package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleAnimation;
import protocolsupport.protocol.types.UsedHand;

public class Animation extends MiddleAnimation {

	public Animation(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		clientdata.readInt();
		clientdata.readByte();
		hand = UsedHand.MAIN;
	}

}
