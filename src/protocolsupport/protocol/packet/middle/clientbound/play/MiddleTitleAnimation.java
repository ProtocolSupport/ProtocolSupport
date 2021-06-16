package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleTitleAnimation extends ClientBoundMiddlePacket {

	protected MiddleTitleAnimation(MiddlePacketInit init) {
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
