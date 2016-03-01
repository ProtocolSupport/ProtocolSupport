package protocolsupport.protocol.transformer.utils;

import io.netty.buffer.ByteBuf;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.RecyclablePacketDataSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable;
import protocolsupport.utils.netty.ChannelUtils;

public class ChunkTransformer {

	protected int coulmnsCount;
	protected Palette palette;
	protected int[] blockdata;
	protected byte[] otherdata;

	public ChunkTransformer(byte[] data19, int bitmap) {
		this.coulmnsCount = Integer.bitCount(bitmap);
		RecyclablePacketDataSerializer chunkdata = RecyclablePacketDataSerializer.create(ProtocolVersion.getLatest(), data19);
		try {
			byte bitsPerBlock = chunkdata.readByte();
			if (bitsPerBlock != 0) {
				int[] paletted = new int[chunkdata.readVarInt()];
				for (int i = 0; i < paletted.length; i++) {
					paletted[i] = chunkdata.readVarInt();
				}
				this.palette = new Palette(paletted);
			} else {
				this.palette = GlobalPalette.INSTANCE;
			}
			int singleblockbytes = chunkdata.readVarInt() * 8 / 4096;
			switch (singleblockbytes) {
				case 1: {
					blockdata = readByteArray(chunkdata);
					break;
				}
				case 2: {
					blockdata = readShortArray(chunkdata);
					break;
				}
				default: {
					throw new IllegalArgumentException(singleblockbytes + " bytes per block is not supported at the moment");
				}
			}
			otherdata = ChannelUtils.toArray(chunkdata);
		} finally {
			chunkdata.release();
		}
	}

	public byte[] toPre18Data(ProtocolVersion version) {
		RemappingTable table = IdRemapper.BLOCK.getTable(version);
		byte[] data = new byte[(4096 + 2048) * coulmnsCount + otherdata.length];
		int tIndex = 0;
		int mIndex = coulmnsCount * 4096;
		for (int blockinfo : blockdata) {
			int blockstate = palette.getBlockState(blockdata[blockinfo]);
			int blockid = blockstate >> 4;
			byte blockdata = (byte) (blockstate & 0xF);
			data[tIndex] = (byte) table.getRemap(blockid);
			if ((mIndex & 1) == 0) {
				data[mIndex] = blockdata;
			} else {
				data[mIndex] |= (blockdata << 4);
			}
			if ((tIndex & 1) == 1) {
				mIndex++;
			}
			tIndex++;
		}
		System.arraycopy(otherdata, 0, data, (4096 + 2048) * coulmnsCount, otherdata.length);
		return data;
	}

	private static int[] readByteArray(ByteBuf buf) {
		int[] data = new int[4096];
		for (int i = 0; i < data.length; i++) {
			data[i] = buf.readUnsignedByte();
		}
		return data;
	}

	private static int[] readShortArray(ByteBuf buf) {
		int[] data = new int[4096];
		for (int i = 0; i < data.length; i++) {
			data[i] = buf.readUnsignedShort();
		}
		return data;
	}

	private static class Palette {
		protected int[] palette;
		public Palette(int[] palette) {
			this.palette = palette;
		}

		public int getBlockState(int index) {
			return palette[index];
		}
	}

	private static class GlobalPalette extends Palette {

		public static final GlobalPalette INSTANCE = new GlobalPalette();

		private GlobalPalette() {
			super(new int[0]);
		}

		@Override
		public int getBlockState(int index) {
			return index;
		}  
	}

}
