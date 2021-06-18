package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleUpdateViewDistance extends ClientBoundMiddlePacket {

	protected int distance;

	protected MiddleUpdateViewDistance(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void decode(ByteBuf serverdata) {
		distance = VarNumberCodec.readVarInt(serverdata);
	}

}
