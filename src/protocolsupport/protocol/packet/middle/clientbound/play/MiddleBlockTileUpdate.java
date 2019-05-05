package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.storage.netcache.ChunkCache;
import protocolsupport.protocol.storage.netcache.ChunkCache.CachedChunk;
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

	protected CachedChunk cachedChunk;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		int type = serverdata.readUnsignedByte();
		NBTCompound tag = ItemStackSerializer.readTag(serverdata);
		tile = new TileEntity(TileEntityType.getByNetworkId(type), position, tag);

		int x = tile.getPosition().getX();
		int y = tile.getPosition().getY();
		int z = tile.getPosition().getZ();
		cachedChunk = chunkCache.get(ChunkCoord.fromGlobal(x, z));
		tile = tileRemapper.remap(
			tile, tileRemapper.tileThatNeedsBlockData(tile.getType()) ? cachedChunk.getBlock(y >> 4, CachedChunk.getBlockIndex(x & 0xF, y & 0xF, z & 0xF)) : -1
		);
		cachedChunk.getTiles(tile.getPosition().getY() >> 4).put(tile.getPosition(), tile);
	}

}
