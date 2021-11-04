package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleWorldBorderLerpSize extends ClientBoundMiddlePacket {

	protected MiddleWorldBorderLerpSize(IMiddlePacketInit init) {
		super(init);
	}

	protected double oldSize;
	protected double newSize;
	protected long speed;

	@Override
	protected void decode(ByteBuf serverdata) {
		oldSize = serverdata.readDouble();
		newSize = serverdata.readDouble();
		speed = VarNumberCodec.readVarLong(serverdata);
	}

}
