package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.utils.types.ChunkCoord;

public abstract class MiddleUnloadChunk extends ClientBoundMiddlePacket {

	public MiddleUnloadChunk(ConnectionImpl connection) {
		super(connection);
	}

	protected ChunkCoord chunk;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		chunk = PositionSerializer.readChunkCoord(serverdata);
	}

}
