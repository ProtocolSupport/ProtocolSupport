package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class MiddleSetViewCenter extends ClientBoundMiddlePacket {

	protected ChunkCoord chunk;

	protected MiddleSetViewCenter(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void decode(ByteBuf serverdata) {
		chunk = PositionCodec.readVarIntChunkCoord(serverdata);
	}

}
