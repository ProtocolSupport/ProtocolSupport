package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class MiddleChunkUnload extends ClientBoundMiddlePacket {

	public MiddleChunkUnload(ConnectionImpl connection) {
		super(connection);
	}

	protected ChunkCoord chunk;

	@Override
	public void readServerData(ByteBuf serverdata) {
		chunk = PositionSerializer.readIntChunkCoord(serverdata);
	}

}
