package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class MiddleChunkUnload extends ClientBoundMiddlePacket {

	protected MiddleChunkUnload(IMiddlePacketInit init) {
		super(init);
	}

	protected ChunkCoord chunk;

	@Override
	protected void decode(ByteBuf serverdata) {
		chunk = PositionCodec.readIntChunkCoord(serverdata);
	}

}
