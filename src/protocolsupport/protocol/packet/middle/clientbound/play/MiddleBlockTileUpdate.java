package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.storage.netcache.chunk.ChunkCache;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.TileEntityType;
import protocolsupport.protocol.types.nbt.NBTCompound;

public abstract class MiddleBlockTileUpdate extends MiddleBlock {

	protected final ChunkCache chunkCache = cache.getChunkCache();
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	public MiddleBlockTileUpdate(ConnectionImpl connection) {
		super(connection);
	}

	protected TileEntity tile;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		int type = serverdata.readUnsignedByte();
		NBTCompound tag = ItemStackSerializer.readTag(serverdata);
		tile = new TileEntity(TileEntityType.getByNetworkId(type), position, tag);
	}

	@Override
	public boolean postFromServerRead() {
		int x = tile.getPosition().getX();
		int y = tile.getPosition().getY();
		int z = tile.getPosition().getZ();
		CachedChunk cachedChunk = chunkCache.get(ChunkCoord.fromGlobal(x, z));
		if (cachedChunk != null) {
			int sectionNumber = y >> 4;
			tile = tileRemapper.remap(
				tile, tileRemapper.tileThatNeedsBlockData(tile.getType()) ? cachedChunk.getBlock(sectionNumber, CachedChunk.getBlockIndex(x & 0xF, y & 0xF, z & 0xF)) : -1
			);
			cachedChunk.getTiles(sectionNumber).put(tile.getPosition(), tile);
			return true;
		} else {
			return false;
		}
	}

}
