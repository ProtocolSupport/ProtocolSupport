package protocolsupport.protocol.legacyremapper.chunk;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.chunk.blockstorage.BlockStorage;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable;
import protocolsupport.utils.netty.ChannelUtils;

public class ChunkTransformer {

	protected int columnsCount;
	protected boolean hasSkyLight;
	protected boolean hasBiomeData;
	protected final ChunkSection[] sections = new ChunkSection[16];
	protected final byte[] biomeData = new byte[256];

	public void loadData(byte[] data19, int bitmap, boolean hasSkyLight, boolean hasBiomeData) {
		this.columnsCount = Integer.bitCount(bitmap);
		this.hasSkyLight = hasSkyLight;
		this.hasBiomeData = hasBiomeData;
		ByteBuf chunkdata = Unpooled.wrappedBuffer(data19);
		for (int i = 0; i < this.columnsCount; i++) {
			sections[i] = new ChunkSection(chunkdata, hasSkyLight);
		}
		if (hasBiomeData) {
			chunkdata.readBytes(biomeData);
		}
	}

	public byte[] toPre18Data(ProtocolVersion version) throws IOException {
		RemappingTable table = IdRemapper.BLOCK.getTable(version);
		byte[] data = new byte[(hasSkyLight ? 10240 : 8192) * columnsCount + 256];
		int blockIdIndex = 0;
		int blockDataIndex = 4096 * columnsCount;
		int blockLightIndex = 6144 * columnsCount;
		int skyLightIndex = 8192 * columnsCount;
		for (int i = 0; i < columnsCount; i++) {
			ChunkSection section = sections[i];
			BlockStorage storage = section.blockdata;
			int blockdataacc = 0;
			for (int block = 0; block < 4096; block++) {
				int blockstate = storage.getBlockState(block);
				blockstate = table.getRemap(blockstate);
				data[blockIdIndex + block] = (byte) (blockstate >> 4);
				byte blockdata = (byte) (blockstate & 0xF);
				if ((block & 1) == 0) {
					blockdataacc = blockdata;
				} else {
					blockdataacc |= (blockdata << 4);
					data[(block >> 1) + blockDataIndex] = (byte) blockdataacc;
				}
			}
			blockIdIndex += 4096;
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
		int blockIdIndex = 0;
		int blockLightIndex = 8192 * columnsCount;
		int skyLightIndex = 10240 * columnsCount;
		for (int i = 0; i < columnsCount; i++) {
			ChunkSection section = sections[i];
			BlockStorage storage = section.blockdata;
			for (int block = 0; block < 4096; block++) {
				int dataindex = blockIdIndex + (block << 1);
				int blockstate = storage.getBlockState(block);
				blockstate = table.getRemap(blockstate);
				data[dataindex] = (byte) blockstate;
				data[dataindex + 1] = (byte) (blockstate >> 8);
			}
			blockIdIndex += 8192;
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

		private static final int[] globalpalette = new int[Short.MAX_VALUE * 2];
		static {
			for (int i = 0; i < globalpalette.length; i++) {
				globalpalette[i] = i;
			}
		}

		protected final BlockStorage blockdata;
		protected final byte[] blocklight = new byte[2048];
		protected final byte[] skylight = new byte[2048];

		public ChunkSection(ByteBuf datastream, boolean hasSkyLight) {
			byte bitsPerBlock = datastream.readByte();
			int[] palette = globalpalette;
			int palettelength = ChannelUtils.readVarInt(datastream);
			if (palettelength != 0) {
				palette = new int[palettelength];
				for (int i = 0; i < palette.length; i++) {
					palette[i] = ChannelUtils.readVarInt(datastream);
				}
			}
			this.blockdata = BlockStorage.create(palette, bitsPerBlock, ChannelUtils.readVarInt(datastream));
			this.blockdata.readFromStream(datastream);
			datastream.readBytes(blocklight);
			if (hasSkyLight) {
				datastream.readBytes(skylight);
			}
		}

	}

}
