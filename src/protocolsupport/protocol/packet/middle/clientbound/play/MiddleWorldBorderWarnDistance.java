package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleWorldBorderWarnDistance extends ClientBoundMiddlePacket {

	protected MiddleWorldBorderWarnDistance(MiddlePacketInit init) {
		super(init);
	}

	protected int warnDistance;

	@Override
	protected void decode(ByteBuf serverdata) {
		warnDistance = VarNumberSerializer.readVarInt(serverdata);
	}

}
