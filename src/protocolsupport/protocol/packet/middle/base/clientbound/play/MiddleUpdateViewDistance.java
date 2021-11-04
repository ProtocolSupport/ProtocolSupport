package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleUpdateViewDistance extends ClientBoundMiddlePacket {

	protected int distance;

	protected MiddleUpdateViewDistance(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void decode(ByteBuf serverdata) {
		distance = VarNumberCodec.readVarInt(serverdata);
	}

}
