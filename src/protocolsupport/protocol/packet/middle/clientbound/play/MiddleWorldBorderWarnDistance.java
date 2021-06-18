package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleWorldBorderWarnDistance extends ClientBoundMiddlePacket {

	protected MiddleWorldBorderWarnDistance(MiddlePacketInit init) {
		super(init);
	}

	protected int warnDistance;

	@Override
	protected void decode(ByteBuf serverdata) {
		warnDistance = VarNumberCodec.readVarInt(serverdata);
	}

}
