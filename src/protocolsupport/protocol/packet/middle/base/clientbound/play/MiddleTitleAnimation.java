package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleTitleAnimation extends ClientBoundMiddlePacket {

	protected MiddleTitleAnimation(IMiddlePacketInit init) {
		super(init);
	}

	protected int fadeIn;
	protected int stay;
	protected int fadeOut;

	@Override
	protected void decode(ByteBuf serverdata) {
		fadeIn = serverdata.readInt();
		stay = serverdata.readInt();
		fadeOut = serverdata.readInt();
	}

}
