package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleWorldBorderWarnDelay extends ClientBoundMiddlePacket {

	protected MiddleWorldBorderWarnDelay(MiddlePacketInit init) {
		super(init);
	}

	protected int warnDelay;

	@Override
	protected void decode(ByteBuf serverdata) {
		warnDelay = VarNumberCodec.readVarInt(serverdata);
	}

}
