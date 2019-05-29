package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.Map;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ChunkCache;
import protocolsupport.protocol.storage.netcache.ChunkCache.CachedChunk;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.BlockStorageReader;
import protocolsupport.protocol.types.chunk.BlocksSection;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.utils.Utils;

public abstract class MiddleChunk extends ClientBoundMiddlePacket {

	protected final ChunkCache chunkCache = cache.getChunkCache();
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	public MiddleChunk(ConnectionImpl connection) {
		super(connection);
	}

	protected ChunkCoord coord;
	protected boolean full;
	protected int blockMask;
	protected NBTCompound heightmaps;
	protected final int[] biomeData = new int[256];

	protected CachedChunk cachedChunk;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		coord = PositionSerializer.readIntChunkCoord(serverdata);
		full = serverdata.readBoolean();
		cachedChunk = chunkCache.get(coord);
		if (cachedChunk == null) {
			cachedChunk = chunkCache.add(coord);
		}
		blockMask = VarNumberSerializer.readVarInt(serverdata);
		heightmaps = ItemStackSerializer.readTag(serverdata);

		{
			ByteBuf chunkdata = ArraySerializer.readVarIntByteArraySlice(serverdata);
			for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
				if (Utils.isBitSet(blockMask, sectionNumber)) {
					BlocksSection section = new BlocksSection(chunkdata.readShort());
					Map<Position, TileEntity> tiles = cachedChunk.getTiles(sectionNumber);
					tiles.clear();

					byte bitsPerBlock = chunkdata.readByte();
					int[] palette = ChunkConstants.GLOBAL_PALETTE;
					if (bitsPerBlock != ChunkConstants.GLOBAL_PALETTE_BITS_PER_BLOCK) {
						palette = ArraySerializer.readVarIntVarIntArray(chunkdata);
					}

					BlockStorageReader reader = new BlockStorageReader(palette, bitsPerBlock, VarNumberSerializer.readVarInt(chunkdata));
					reader.readFromStream(chunkdata);
					for (int blockIndex = 0; blockIndex < ChunkConstants.BLOCKS_IN_SECTION; blockIndex++) {
						int blockdata = reader.getBlockData(blockIndex);
						section.setBlockData(blockIndex, (short) blockdata);
						if (tileRemapper.usedToBeTile(blockdata)) {
							Position position = new Position(
								(coord.getX() << 4) + (blockIndex & 0xF),
								(sectionNumber << 4) + ((blockIndex >> 8) & 0xF),
								(coord.getZ() << 4) + ((blockIndex >> 4) & 0xF)
							);
							tiles.put(position, tileRemapper.getLegacyTileFromBlock(position, blockdata));
						}
					}

					cachedChunk.setBlocksSection(sectionNumber, section);
				}
			}
			if (full) {
				for (int i = 0; i < biomeData.length; i++) {
					biomeData[i] = chunkdata.readInt();
				}
			}
		}

		int tileCount = VarNumberSerializer.readVarInt(serverdata);
		for (int i = 0; i < tileCount; i++) {
			TileEntity tile = new TileEntity(ItemStackSerializer.readTag(serverdata));
			Position position = tile.getPosition();
			int y = position.getY();
			int sectionNumber = y >> 4;
			if (tileRemapper.tileThatNeedsBlockData(tile.getType())) {
				int blockdata = cachedChunk.getBlock(sectionNumber, CachedChunk.getBlockIndex(position.getX() & 0xF, y & 0xF, position.getZ() & 0xF));
				tile = tileRemapper.remap(tile, blockdata);
			} else {
				tile = tileRemapper.remap(tile, -1);
			}
			cachedChunk.getTiles(sectionNumber).put(tile.getPosition(), tile);
		}
	}


}
