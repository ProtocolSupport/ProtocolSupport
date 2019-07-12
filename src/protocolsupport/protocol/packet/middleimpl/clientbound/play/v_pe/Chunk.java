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
import protocolsupport.protocol.storage.netcache.chunk.CachedChunk;
import protocolsupport.protocol.typeremapper.chunk.BlockStorageWriterPE;
import protocolsupport.protocol.typeremapper.pe.PEBlocks;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.EmptyChunk;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.protocol.types.chunk.ChunkSectonBlockData;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

import static protocolsupport.protocol.typeremapper.utils.RemappingTable.ArrayBasedIdRemappingTable;

public class Chunk extends MiddleChunk {

	protected static final int SUBCHUNK_VERSION = 8;

	protected final ArrayBasedIdRemappingTable blockRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final ArrayBasedIdRemappingTable biomeRemappingTable = PEDataValues.BIOME.getTable(version);
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);
	protected final short[] waterlogPalette = new short[] {
		(short) PEBlocks.getPEAirId(version),
		(short) PEBlocks.getPEWaterId(version)
	};

	public Chunk(ConnectionImpl connection) {
		super(connection);
	}

	public RecyclableCollection<ClientBoundPacketData> toData() {
		if (!full && (blockMask & 0xF) != 0xF) { //request full chunk if not given
			InternalPluginMessageRequest.receivePluginMessageRequest(connection, new ChunkUpdateRequest(coord));
			return RecyclableEmptyList.get();
		}
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		ClientBoundPacketData chunkpacket = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA);
		cache.getPEChunkMapCache().markSent(coord);

		PositionSerializer.writePEChunkCoord(chunkpacket, coord);
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_12)) {
			chunkpacket.writeShortLE(ChunkConstants.SECTION_COUNT_BLOCKS);
		}
		ArraySerializer.writeVarIntByteArray(chunkpacket, chunkdata -> {
			if (version.isBefore(ProtocolVersion.MINECRAFT_PE_1_12)) {
				chunkdata.writeByte(ChunkConstants.SECTION_COUNT_BLOCKS);
			}
			for (int sectionNumber = 0; sectionNumber < ChunkConstants.SECTION_COUNT_BLOCKS; sectionNumber++) {
				if (Utils.isBitSet(blockMask, sectionNumber)) {
					ChunkSectonBlockData section = sections[sectionNumber];
					int bitsPerBlock = getPocketBitsPerBlock(section.getBitsPerBlock());
					BlockStorageWriterPE blockstorage = new BlockStorageWriterPE(bitsPerBlock);
					BlockStorageWriterPE waterstorage = new BlockStorageWriterPE(1);

					int peIndex = 0; //subchunk iterator order is different for PE
					for (int x = 0; x < 16; x++) {
						for (int z = 0; z < 16; z++) {
							for (int y = 0; y < 16; y++) {
								int pcIndex = getPcIndex(x, y, z);
								int runtimeId = section.getRuntimeId(pcIndex);
								int pcId = section.getPalette()[runtimeId];
								blockstorage.setBlockState(peIndex, runtimeId);
								if (PEBlocks.isPCBlockWaterlogged(pcId)) {
									waterstorage.setBlockState(peIndex, 1);
								}
								peIndex++;
							}
						}
					}

					chunkdata.writeByte(SUBCHUNK_VERSION);
					chunkdata.writeByte(2); //blockstate and waterlog
					blockstorage.writeTo(chunkdata);
					writePalette(section.getPalette(), chunkdata, true);
					waterstorage.writeTo(chunkdata);
					writePalette(waterlogPalette, chunkdata, false);
				} else {
					writeEmptySubChunk(chunkdata, version);
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

	protected void writePalette(short[] palette, ByteBuf to, boolean shouldRemap) {
		VarNumberSerializer.writeSVarInt(to, palette.length);
		for (int id : palette) {
			if (shouldRemap) {
				id = PEBlocks.getPocketRuntimeId(blockRemappingTable.getRemap(id & 0xFFFF));
			}
			VarNumberSerializer.writeSVarInt(to, id);
		}
	}

	public static void addFakeChunks(RecyclableCollection<ClientBoundPacketData> packets, ChunkCoord coord, ProtocolVersion version) {
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				packets.add(createEmptyChunk(new ChunkCoord(coord.getX() + x, coord.getZ() + z), version));
			}
		}
	}

	public static void writeEmptySubChunk(ByteBuf out, ProtocolVersion version) {
		out.writeByte(SUBCHUNK_VERSION);
		out.writeByte(1); //only blockstate storage
		BlockStorageWriterPE.writeEmpty(out);
		VarNumberSerializer.writeSVarInt(out, 1); //Palette size
		VarNumberSerializer.writeSVarInt(out, PEBlocks.getPEAirId(version)); //Palette: Air
	}

	public static void writeEmptyChunk(ByteBuf out, ChunkCoord chunk, ProtocolVersion version) {
		PositionSerializer.writePEChunkCoord(out, chunk);
		if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_PE_1_12)) {
			out.writeBytes(EmptyChunk.getPEChunkData112());
		} else {
			out.writeBytes(EmptyChunk.getPEChunkData());
		}
	}

	public static ClientBoundPacketData createEmptyChunk(ChunkCoord chunk, ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHUNK_DATA);
		writeEmptyChunk(serializer, chunk, version);
		return serializer;
	}

	protected static final int getPocketBitsPerBlock(int pcBitsPerBlock) {
		if (pcBitsPerBlock == 7) {
			return 8;
		} else if (pcBitsPerBlock > 8) {
			return 16;
		}
		return pcBitsPerBlock;
	}

	protected static final int getPcIndex(int x, int y, int z) {
		return (y << 8) | (z << 4) | (x);
	}

}