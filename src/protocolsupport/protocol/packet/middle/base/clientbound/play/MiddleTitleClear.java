package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleTitleClear extends ClientBoundMiddlePacket {

	protected MiddleTitleClear(IMiddlePacketInit init) {
		super(init);
	}

	protected boolean reset;

	@Override
	protected void decode(ByteBuf serverdata) {
		reset = serverdata.readBoolean();
	}

}
