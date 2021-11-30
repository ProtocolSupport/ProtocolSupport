package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleUpdateSimulationDistance extends ClientBoundMiddlePacket {

	protected MiddleUpdateSimulationDistance(IMiddlePacketInit init) {
		super(init);
	}

	protected int distance;

	@Override
	protected void decode(ByteBuf serverdata) {
		distance = VarNumberCodec.readVarInt(serverdata);
	}

}
