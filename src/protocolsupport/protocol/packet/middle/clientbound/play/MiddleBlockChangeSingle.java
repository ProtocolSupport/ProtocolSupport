package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ChunkCache;
import protocolsupport.protocol.storage.netcache.ChunkCache.CachedChunk;
import protocolsupport.protocol.types.ChunkCoord;

public abstract class MiddleBlockChangeSingle extends MiddleBlock {

	protected final ChunkCache chunkCache = cache.getChunkCache();

	public MiddleBlockChangeSingle(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		id = VarNumberSerializer.readVarInt(serverdata);
	}

	@Override
	public boolean postFromServerRead() {
		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();
		CachedChunk cachedChunk = cache.getChunkCache().get(ChunkCoord.fromGlobal(x, z));
		if (cachedChunk != null) {
			cachedChunk.setBlock(y >> 4, CachedChunk.getBlockIndex(x & 0xF, y & 0xF, z & 0xF), id);
			return true;
		} else {
			return false;
		}
	}

}