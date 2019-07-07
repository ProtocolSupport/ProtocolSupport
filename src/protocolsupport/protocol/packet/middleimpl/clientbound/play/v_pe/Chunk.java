package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;

import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.listeners.internal.ChunkUpdateRequest;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.typeremapper.chunk.BlockStorageWriterPE;
import protocolsupport.protocol.typeremapper.pe.PEBlocks;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.EmptyChunk;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.typeremapper.utils.RemappingTable;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.chunk.ChunkSectonBlockData;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class Chunk extends MiddleChunk {

	protected static final int FLAG_RUNTIME = 1;
	protected static final int SUBCHUNK_VERSION = 8;

	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (!full) { //request full chunk if not given, but still send partial chunk
			InternalPluginMessageRequest.receivePluginMessageRequest(connection, new ChunkUpdateRequest(coord));
			return RecyclableEmptyList.get();
		}
		RemappingTable.ArrayBasedIdRemappingTable blockRemappingTable = LegacyBlockData.REGISTRY.getTable(connection.getVersion());
		RemappingTable.ArrayBasedIdRemappingTable biomeRemappingTable = PEDataValues.BIOME.getTable(connection.getVersion());
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData chunkpacket = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA);
		PositionSerializer.writePEChunkCoord(chunkpacket, coord);

		//1.12 does section count first, then payload. earlier does payload including section count
		if (/* IS_1.12 */false) {
			chunkpacket.writeByte(ChunkConstants.SECTION_COUNT_BLOCKS);
		}
		ArraySerializer.writeVarIntByteArray(chunkpacket, chunkdata -> {
			if (/* !IS_1.12 */true) {
				chunkdata.writeByte(ChunkConstants.SECTION_COUNT_BLOCKS);
			}
			for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
				ChunkSectonBlockData section = sections[sectionNumber];
				chunkdata.writeByte(SUBCHUNK_VERSION);
				if (section != null) {
					int[] palette = new int[section.getPalette().length];
					for (int i = 0 ; i < palette.length ; i++) { //generate palette first
						int pcId = section.getPalette()[i] & 0xFFFF;
						palette[i] = PEBlocks.getPocketRuntimeId(blockRemappingTable.getRemap(pcId));
					}
					int bitsPerBlock = getPocketBitsPerBlock(section.getBitsPerBlock());
					BlockStorageWriterPE blockstorage = new BlockStorageWriterPE(bitsPerBlock, ChunkConstants.BLOCKS_IN_SECTION);
					BlockStorageWriterPE waterstorage = new BlockStorageWriterPE(1, ChunkConstants.BLOCKS_IN_SECTION); //Waterlogged -> second storage. Only true/false per block
					int peIndex = 0;
					for (int x = 0; x < 16; x++) { for (int z = 0; z < 16; z++) { for (int y = 0; y < 16; y++) {
						int pcIndex = getPcIndex(x, y, z);
						int runtimeId = section.getRuntimeId(pcIndex);
						int pcId = section.getPalette()[runtimeId];
						blockstorage.setBlockState(peIndex, runtimeId);
						if (PEBlocks.isPCBlockWaterlogged(pcId)) {
							waterstorage.setBlockState(peIndex, 1);
						}
						peIndex++;
					}}}
					chunkdata.writeByte(2); //blockstorage count.
					chunkdata.writeByte((bitsPerBlock << 1) | FLAG_RUNTIME);
					for (int word : blockstorage.getBlockData()) {
						chunkdata.writeIntLE(word);
					}
					ArraySerializer.writeSVarIntSVarIntArray(chunkdata, palette);
					chunkdata.writeByte((1 << 1) | FLAG_RUNTIME); //Water storage.
					for (int word : waterstorage.getBlockData()) {
						chunkdata.writeIntLE(word);
					}
					VarNumberSerializer.writeSVarInt(chunkdata, 2); //Palette size
					VarNumberSerializer.writeSVarInt(chunkdata, 0); //Palette air
					VarNumberSerializer.writeSVarInt(chunkdata, 54); //Palette water
				} else {
					chunkdata.writeByte(1); //blockstorage count.
					chunkdata.writeByte((1 << 1) | FLAG_RUNTIME);
					chunkdata.writeZero(512);
					VarNumberSerializer.writeSVarInt(chunkdata, 1); //Palette size
					VarNumberSerializer.writeSVarInt(chunkdata, 0); //Palette: Air
				}
			}
			chunkdata.writeZero(512); //heightmap (will be recalculated by client anyway)
			for (int i = 0; i < biomeData.length; i++) {
				chunkdata.writeByte(biomeRemappingTable.getRemap(biomeData[i]));
			}
			chunkdata.writeByte(0); //borders
			for (TileEntity tile : tiles) {
				Position position = tile.getPosition();
				int y = position.getY();
				int sectionNumber = y >> 4;
				if (tileRemapper.tileThatNeedsBlockData(tile.getType())) {
					int blockIndex = CachedChunk.getBlockIndex(position.getX() & 0xF, y & 0xF, position.getZ() & 0xF);
					tile = tileRemapper.remap(tile, sections[sectionNumber].getBlockData(blockIndex));
				} else {
					tile = tileRemapper.remap(tile);
				}
				ItemStackSerializer.writeTag(chunkdata, true, version, tile.getNBT());
			}
		});

		packets.add(chunkpacket);
		return packets;
	}

	protected static int getPocketBitsPerBlock(int pcBitsPerBlock) {
		if (pcBitsPerBlock == 7) {
			return 8;
		} else if (pcBitsPerBlock > 8) {
			return 16;
		}
		return pcBitsPerBlock;
	}

	protected static int getPcIndex(int x, int y, int z) {
		return (y << 8) | (z << 4) | (x);
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

	/*public static class BlockStorageWriterPE {

		public final int[] blockdata;
		public final int bitsPerBlock;
		public final int blocksPerWord;
		public final int singleValMask;

		public BlockStorageWriterPE(int bitsPerBlock) {
			assert(bitsPerBlock <= Integer.SIZE);
			this.bitsPerBlock = bitsPerBlock;
			blocksPerWord = Integer.SIZE / bitsPerBlock;
			blockdata = new int[(ChunkConstants.BLOCKS_IN_SECTION + blocksPerWord - 1) / blocksPerWord];
			singleValMask = (1 << bitsPerBlock) - 1;
		}

		public void setBlockState(int index, int blockstate) {
			final int arrIndex = index / blocksPerWord;
			final int bitIndex = (index % blocksPerWord) * bitsPerBlock;
			if (singleValMask >> bitsPerBlock != 0) {
				throw new IllegalArgumentException("Block ID too large for palette's bitsPerBlock");
			}
			//blockdata[arrIndex] = ((blockdata[arrIndex] & ~(singleValMask << bitIndex)) | ((blockstate & singleValMask) << bitIndex));
			blockdata[arrIndex] = blockdata[arrIndex] | (blockstate << bitIndex);
		}

	}*/

}