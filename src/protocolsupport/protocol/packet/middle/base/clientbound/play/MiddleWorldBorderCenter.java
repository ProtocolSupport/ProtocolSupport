package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleWorldBorderCenter extends ClientBoundMiddlePacket {

	protected MiddleWorldBorderCenter(IMiddlePacketInit init) {
		super(init);
	}

	protected double x;
	protected double z;

	@Override
	protected void decode(ByteBuf serverdata) {
		x = serverdata.readDouble();
		z = serverdata.readDouble();
	}

}
