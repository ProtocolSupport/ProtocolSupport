package protocolsupport.protocol.legacyremapper.chunk;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.Utils;

public abstract class ChunkTransformer {

	public static enum BlockFormat {
		VARIES, //format for 1.9+
		SHORT, //format for 1.8
		BYTE, //format for 1.7-
		PE, //format for MCPE
	}

	public static ChunkTransformer create(BlockFormat format) {
		switch (format) {
			case VARIES: {
				return new ChunkTransformerVaries();
			}
			case SHORT: {
				return new ChunkTransformerShort();
			}
			case BYTE: {
				return new ChunkTransformerByte();
			}
			case PE: {
				return new ChunkTransformerPE();
			}
			default: {
				throw new IllegalArgumentException();
			}
		}
	}

	protected byte[] data;
	protected int bitmap;

	protected int columnsCount;
	protected boolean hasSkyLight;
	protected boolean hasBiomeData;
	protected final ChunkSection[] sections = new ChunkSection[16];
	protected final byte[] biomeData = new byte[256];

	public void loadData(byte[] data, int bitmap, boolean hasSkyLight, boolean hasBiomeData) {
		try {
			this.data = data;
			this.bitmap = bitmap;
			this.columnsCount = Integer.bitCount(bitmap);
			this.hasSkyLight = hasSkyLight;
			this.hasBiomeData = hasBiomeData;
			ByteBuf chunkdata = Unpooled.wrappedBuffer(data);
			for (int i = 0; i < this.columnsCount; i++) {
				sections[i] = new ChunkSection(chunkdata, hasSkyLight);
			}
			if (hasBiomeData) {
				chunkdata.readBytes(biomeData);
			}
		} catch (Throwable e) {
			throw new ChunkDataParseException(data, bitmap, hasSkyLight, hasBiomeData, e);
		}
	}

	public byte[] toLegacyData(ProtocolVersion version) {
		try {
			return toLegacyData0(version);
		} catch (Throwable t) {
			throw new ChunkDataParseException(data, bitmap, hasSkyLight, hasBiomeData, t);
		}
	}

	protected abstract byte[] toLegacyData0(ProtocolVersion version);

	protected static final int blocksInSection = 16 * 16 * 16;

	protected static class ChunkSection {

		private static final int[] globalpalette = new int[Short.MAX_VALUE * 2];
		static {
			for (int i = 0; i < globalpalette.length; i++) {
				globalpalette[i] = i;
			}
		}

		protected final BlockStorageReader blockdata;
		protected final byte[] blocklight = new byte[2048];
		protected final byte[] skylight = new byte[2048];

		public ChunkSection(ByteBuf datastream, boolean hasSkyLight) {
			byte bitsPerBlock = datastream.readByte();
			int[] palette = globalpalette;
			int palettelength = VarNumberSerializer.readVarInt(datastream);
			if (palettelength != 0) {
				palette = new int[palettelength];
				for (int i = 0; i < palette.length; i++) {
					palette[i] = VarNumberSerializer.readVarInt(datastream);
				}
			}
			this.blockdata = new BlockStorageReader(palette, bitsPerBlock, VarNumberSerializer.readVarInt(datastream));
			this.blockdata.readFromStream(datastream);
			datastream.readBytes(blocklight);
			if (hasSkyLight) {
				datastream.readBytes(skylight);
			}
		}

	}

	public static final class ChunkDataParseException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ChunkDataParseException(byte[] data, int bitmap, boolean hasSkyLight, boolean hasBiomeData, Throwable e) {
			super(Utils.exceptionMessage(
				"Failed to parse chunk data",
				String.format("Column bitmap: %s (Column count: %d)", Integer.toBinaryString(bitmap), Integer.bitCount(bitmap)),
				String.format("Has sky light: %b", hasSkyLight),
				String.format("Has biome data: %b", hasBiomeData),
				String.format("Chunk data: %s", Arrays.toString(data))
			), e);
		}
	}

}
