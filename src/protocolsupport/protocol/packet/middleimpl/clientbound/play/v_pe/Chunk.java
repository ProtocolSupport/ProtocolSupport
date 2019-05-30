package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.listeners.internal.ChunkUpdateRequest;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.TileDataCache;
import protocolsupport.protocol.typeremapper.chunk.BlockPalette;
import protocolsupport.protocol.typeremapper.chunk.BlockStorageWriterPE;
import protocolsupport.protocol.typeremapper.pe.PEBlocks;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkTransformerBB;
import protocolsupport.protocol.typeremapper.chunk.EmptyChunk;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class Chunk extends MiddleChunk {

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	private final ChunkTransformerBB transformer = new ChunkTransformerPE(
		LegacyBlockData.REGISTRY.getTable(connection.getVersion()),
		TileEntityRemapper.getRemapper(connection.getVersion()),
		connection.getCache().getTileCache(),
		PEDataValues.BIOME.getTable(connection.getVersion())
	);

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (full || (bitmask == 0xFFFF)) { //Only send full or 'full' chunks to PE.
			RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
			ProtocolVersion version = connection.getVersion();
			cache.getPEChunkMapCache().markSent(chunk);
			transformer.loadData(chunk, data, bitmask, cache.getAttributesCache().hasSkyLightInCurrentDimension(), full, tiles);
			ClientBoundPacketData chunkpacket = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA);
			PositionSerializer.writePEChunkCoord(chunkpacket, chunk);
			ArraySerializer.writeVarIntByteArray(chunkpacket, chunkdata -> {
				transformer.writeLegacyData(chunkdata);
				chunkdata.writeByte(0); //borders
				for (TileEntity tile : transformer.remapAndGetTiles()) {
					ItemStackSerializer.writeTag(chunkdata, true, version, tile.getNBT());
				}
			});
			packets.add(chunkpacket);
			return packets;
		} else { //Request a full chunk.
			InternalPluginMessageRequest.receivePluginMessageRequest(connection, new ChunkUpdateRequest(chunk));
			return RecyclableEmptyList.get();
		}
	}

	/*public ChunkTransformerPE(RemappingTable.ArrayBasedIdRemappingTable blockRemappingTable, TileEntityRemapper tileremapper, TileDataCache tilecache, RemappingTable.ArrayBasedIdRemappingTable biomeRemappingTable) {
		super(blockRemappingTable, tileremapper, tilecache);
		this.biomeRemappingTable = biomeRemappingTable;
	}

	protected final RemappingTable.ArrayBasedIdRemappingTable biomeRemappingTable;
	protected static final int flag_runtime = 1;*/

	@Override
	public void writeLegacyData(ByteBuf chunkdata) {
		RemappingTable.ArrayBasedIdRemappingTable blockRemappingTable = LegacyBlockData.REGISTRY.getTable(connection.getVersion());
		TileEntityRemapper tileremapper = TileEntityRemapper.getRemapper(connection.getVersion());
		TileDataCache tilecache = connection.getCache().getTileCache();
		RemappingTable.ArrayBasedIdRemappingTable biomeRemappingTable = PEDataValues.BIOME.getTable(connection.getVersion());

		chunkdata.writeByte(sections.length);
		for (int i = 0; i < sections.length; i++) {
			chunkdata.writeByte(8); //subchunk version
			ChunkSection section = sections[i];
			if (section != null) {
				chunkdata.writeByte(2); //blockstorage count.
				BlockStorageReader storage = section.blockdata;
				BlockPalette palette = new BlockPalette();
				int bitsPerBlock = getPocketBitsPerBlock(storage.getBitsPerBlock());
				BlockStorageWriterPE blockstorage = new BlockStorageWriterPE(bitsPerBlock, blocksInSection);
				BlockStorageWriterPE waterstorage = new BlockStorageWriterPE(1, blocksInSection); //Waterlogged -> second storage. Only true/false per block
				chunkdata.writeByte((bitsPerBlock << 1) | flag_runtime);

				int peIndex = 0;
				for (int x = 0; x < 16; x++) { for (int z = 0; z < 16; z++) { for (int y = 0; y < 16; y++) {
					int pcIndex = getPcIndex(x, y, z);
					int blockstate = storage.getBlockData(pcIndex);
					// Update tile entity cache
					processBlockData(i, pcIndex, blockstate);

					// Remap to PE blockstate
					if (PEBlocks.isPCBlockWaterlogged(blockstate)) { waterstorage.setBlockState(peIndex, 1); }
					blockstorage.setBlockState(peIndex, palette.getRuntimeId(PEBlocks.getPocketRuntimeId(blockDataRemappingTable.getRemap(blockstate))));
					peIndex++;
				}}}

				for (int word : blockstorage.getBlockData()) {
					chunkdata.writeIntLE(word);
				}
				ArraySerializer.writeSVarIntSVarIntArray(chunkdata, palette.getBlockStates());
				chunkdata.writeByte((1 << 1) | flag_runtime); //Water storage.
				for (int word : waterstorage.getBlockData()) {
					chunkdata.writeIntLE(word);
				}
				VarNumberSerializer.writeSVarInt(chunkdata, 2); //Palette size
				VarNumberSerializer.writeSVarInt(chunkdata, 0); //Palette air
				VarNumberSerializer.writeSVarInt(chunkdata, 54); //Palette water
			} else {
				chunkdata.writeByte(1); //blockstorage count.
				chunkdata.writeByte((1 << 1) | flag_runtime);
				chunkdata.writeZero(512);
				VarNumberSerializer.writeSVarInt(chunkdata, 1); //Palette size
				VarNumberSerializer.writeSVarInt(chunkdata, 0); //Palette: Air
			}
		}
		chunkdata.writeZero(512); //heightmap (will be recalculated by client anyway)
		for (int i = 0; i < biomeData.length; i++) {
			chunkdata.writeByte(biomeRemappingTable.getRemap(biomeData[i]));
		}
	}

	protected static int getPcIndex(int x, int y, int z) {
		return (y << 8) | (z << 4) | (x);
	}

	protected static int getPocketBitsPerBlock(int pcBitsPerBlock) {
		if (pcBitsPerBlock == 7) {
			return 8;
		} else if (pcBitsPerBlock > 8) {
			return 16;
		}
		return pcBitsPerBlock;
	}

	public static void addFakeChunks(RecyclableCollection<ClientBoundPacketData> packets, ChunkCoord coord) {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				packets.add(createEmptyChunk(new ChunkCoord(coord.getX() + x, coord.getZ() + z)));
			}
		}
	}

	public static void writeEmptyChunk(ByteBuf out, ChunkCoord chunk) {
		PositionSerializer.writePEChunkCoord(out, chunk);
		out.writeBytes(EmptyChunk.getPEChunkData());
	}

	public static ClientBoundPacketData createEmptyChunk(ChunkCoord chunk) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA);
		writeEmptyChunk(serializer, chunk);
		return serializer;
	}

	public static void writeChunkPublisherUpdate(ByteBuf out, int x, int y, int z) {
		VarNumberSerializer.writeSVarInt(out, x);
		VarNumberSerializer.writeVarInt(out, y);
		VarNumberSerializer.writeSVarInt(out, z);
		VarNumberSerializer.writeVarInt(out, 512); //radius, gets clamped by client
	}

	public static ClientBoundPacketData createChunkPublisherUpdate(int x, int y, int z) {
		ClientBoundPacketData networkChunkUpdate = ClientBoundPacketData.create(PEPacketIDs.NETWORK_CHUNK_PUBLISHER_UPDATE_PACKET);
		writeChunkPublisherUpdate(networkChunkUpdate, x, y, z);
		return networkChunkUpdate;
	}

}