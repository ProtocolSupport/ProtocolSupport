package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;
import protocolsupport.protocol.types.ChunkCoord;

//TODO: split the class to legacy and new one, and remove usage of chunk cache from new one
public abstract class MiddleChunkUnload extends ClientBoundMiddlePacket {

	protected final ChunkCache chunkCache = cache.getChunkCache();

	public MiddleChunkUnload(ConnectionImpl connection) {
		super(connection);
	}

	protected ChunkCoord chunk;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		chunk = PositionSerializer.readIntChunkCoord(serverdata);
		chunkCache.remove(chunk);
	}

}
