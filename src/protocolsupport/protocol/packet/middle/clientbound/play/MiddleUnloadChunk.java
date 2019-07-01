package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class MiddleUnloadChunk extends ClientBoundMiddlePacket {

	protected final ChunkCache chunkCache = cache.getChunkCache();

	public MiddleUnloadChunk(ConnectionImpl connection) {
		super(connection);
	}

	protected ChunkCoord chunk;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		chunk = PositionSerializer.readIntChunkCoord(serverdata);
		chunkCache.remove(chunk);
	}

}
