package protocolsupport.protocol.transformer.utils.chunk;

import java.io.IOException;
import java.util.ArrayList;

import io.netty.buffer.Unpooled;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.utils.chunk.Palette.GlobalPalette;
import protocolsupport.protocol.transformer.utils.chunk.blockstorage.BlockStorage;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable;

public class ChunkTransformer {

	protected int columnsCount;
	protected boolean hasSkyLight;
	protected boolean hasBiomeData;
	protected final ArrayList<ChunkSection> sections = new ArrayList<ChunkSection>(32);
	protected final byte[] biomeData = new byte[256];

	public void loadData(byte[] data19, int bitmap, boolean hasSkyLight, boolean hasBiomeData) {
		this.columnsCount = Integer.bitCount(bitmap);
		this.hasSkyLight = hasSkyLight;
		this.hasBiomeData = hasBiomeData;
		this.sections.clear();
		PacketDataSerializer chunkdata = new PacketDataSerializer(Unpooled.wrappedBuffer(data19), ProtocolVersion.getLatest());
		for (int i = 0; i < this.columnsCount; i++) {
			this.sections.add(new ChunkSection(chunkdata, hasSkyLight));
		}
		if (hasBiomeData) {
			chunkdata.readBytes(biomeData);
		}
	}

	public byte[] toPre18Data(ProtocolVersion version) throws IOException {
		RemappingTable table = IdRemapper.BLOCK.getTable(version);
		byte[] data = new byte[(hasSkyLight ? 10240 : 8192) * columnsCount + 256];
		int blockIndex = 0;
		int blockDataIndex = 4096 * columnsCount;
		int blockLightIndex = 6144 * columnsCount;
		int skyLightIndex = 8192 * columnsCount;
		for (ChunkSection section : this.sections) {
			int blockdataacc = 0;
			for (int i = 0; i < 4096; i++) {
				int blockstate = section.palette.getBlockState(section.blockdata.getPaletteIndex(i));
				blockstate = table.getRemap(blockstate);
				data[blockIndex + i] = (byte) (blockstate >> 4);
				byte blockdata = (byte) (blockstate & 0xF);
				if ((i & 1) == 0) {
					blockdataacc = blockdata;
				} else {
					blockdataacc |= (blockdata << 4);
					data[(i >> 1) + blockDataIndex] = (byte) blockdataacc;
				}
			}
			blockIndex += 4096;
			blockDataIndex += 2048;
			System.arraycopy(section.blocklight, 0, data, blockLightIndex, 2048);
			blockLightIndex += 2048;
			if (hasSkyLight) {
				System.arraycopy(section.skylight, 0, data, skyLightIndex, 2048);
				skyLightIndex += 2048;
			}
		}
		if (hasBiomeData) {
			System.arraycopy(biomeData, 0, data, skyLightIndex, 256);
		}
		return data;
	}

	public byte[] to18Data() throws IOException {
		RemappingTable table = IdRemapper.BLOCK.getTable(ProtocolVersion.MINECRAFT_1_8);
		byte[] data = new byte[(hasSkyLight ? 12288 : 10240) * columnsCount + 256];
		int blockIndex = 0;
		int blockLightIndex = 8192 * columnsCount;
		int skyLightIndex = 10240 * columnsCount;
		for (ChunkSection section : this.sections) {
			for (int i = 0; i < 4096; i++) {
				int dataindex = blockIndex + (i << 1);
				int blockstate = section.palette.getBlockState(section.blockdata.getPaletteIndex(i));
				blockstate = table.getRemap(blockstate);
				data[dataindex] = (byte) blockstate;
				data[dataindex + 1] = (byte) (blockstate >> 8);
			}
			blockIndex += 8192;
			System.arraycopy(section.blocklight, 0, data, blockLightIndex, 2048);
			blockLightIndex += 2048;
			if (hasSkyLight) {
				System.arraycopy(section.skylight, 0, data, skyLightIndex, 2048);
				skyLightIndex += 2048;
			}
		}
		if (hasBiomeData) {
			System.arraycopy(biomeData, 0, data, skyLightIndex, 256);
		}
		return data;
	}

	private static class ChunkSection {
		protected final Palette palette;
		protected final BlockStorage blockdata;
		protected final byte[] blocklight = new byte[2048];
		protected final byte[] skylight = new byte[2048];
		public ChunkSection(PacketDataSerializer datastream, boolean hasSkyLight) {
			byte bitsPerBlock = datastream.readByte();
			if (bitsPerBlock != 0) {
				int[] paletted = new int[datastream.readVarInt()];
				for (int i = 0; i < paletted.length; i++) {
					paletted[i] = datastream.readVarInt();
				}
				this.palette = new Palette(paletted);
			} else {
				this.palette = GlobalPalette.INSTANCE;
			}
			this.blockdata = BlockStorage.create(bitsPerBlock, datastream.readVarInt());
			this.blockdata.readFromStream(datastream);
			datastream.readBytes(blocklight);
			if (hasSkyLight) {
				datastream.readBytes(skylight);
			}
		}
	}

}
