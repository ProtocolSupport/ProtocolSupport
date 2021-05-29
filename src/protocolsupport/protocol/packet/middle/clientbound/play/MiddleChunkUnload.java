package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class MiddleChunkUnload extends ClientBoundMiddlePacket {

	protected MiddleChunkUnload(MiddlePacketInit init) {
		super(init);
	}

	protected ChunkCoord chunk;

	@Override
	protected void decode(ByteBuf serverdata) {
		chunk = PositionSerializer.readIntChunkCoord(serverdata);
	}

}
