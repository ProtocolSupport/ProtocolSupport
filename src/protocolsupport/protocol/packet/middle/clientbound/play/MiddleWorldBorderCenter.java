package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleWorldBorderCenter extends ClientBoundMiddlePacket {

	protected MiddleWorldBorderCenter(MiddlePacketInit init) {
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
