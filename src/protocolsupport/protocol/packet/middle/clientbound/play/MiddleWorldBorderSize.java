package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleWorldBorderSize extends ClientBoundMiddlePacket {

	protected MiddleWorldBorderSize(MiddlePacketInit init) {
		super(init);
	}

	protected double size;

	@Override
	protected void decode(ByteBuf serverdata) {
		size = serverdata.readDouble();
	}

}
