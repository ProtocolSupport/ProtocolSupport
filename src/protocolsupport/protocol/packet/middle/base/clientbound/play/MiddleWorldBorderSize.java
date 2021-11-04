package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleWorldBorderSize extends ClientBoundMiddlePacket {

	protected MiddleWorldBorderSize(IMiddlePacketInit init) {
		super(init);
	}

	protected double size;

	@Override
	protected void decode(ByteBuf serverdata) {
		size = serverdata.readDouble();
	}

}
