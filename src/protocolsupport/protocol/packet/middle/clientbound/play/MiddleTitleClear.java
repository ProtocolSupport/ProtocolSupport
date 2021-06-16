package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleTitleClear extends ClientBoundMiddlePacket {

	protected MiddleTitleClear(MiddlePacketInit init) {
		super(init);
	}

	protected boolean reset;

	@Override
	protected void decode(ByteBuf serverdata) {
		reset = serverdata.readBoolean();
	}

}
