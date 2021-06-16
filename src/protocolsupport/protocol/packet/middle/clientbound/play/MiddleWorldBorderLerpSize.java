package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleWorldBorderLerpSize extends ClientBoundMiddlePacket {

	protected MiddleWorldBorderLerpSize(MiddlePacketInit init) {
		super(init);
	}

	protected double oldSize;
	protected double newSize;
	protected long speed;

	@Override
	protected void decode(ByteBuf serverdata) {
		oldSize = serverdata.readDouble();
		newSize = serverdata.readDouble();
		speed = VarNumberSerializer.readVarLong(serverdata);
	}

}
