package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class MiddleSetViewCenter extends ClientBoundMiddlePacket {

	protected ChunkCoord chunk;

	protected MiddleSetViewCenter(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void decode(ByteBuf serverdata) {
		chunk = PositionCodec.readVarIntChunkCoord(serverdata);
	}

}
