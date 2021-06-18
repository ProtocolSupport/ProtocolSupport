package protocolsupport.protocol.types.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.utils.NumberBitsStoragePadded;

public class ChunkSectonBlockData extends NumberBitsStoragePadded {

	public static ChunkSectonBlockData readFromStream(ByteBuf stream) {
		short nonAirBlockCount = stream.readShort();
		byte bitsPerBlock = normalizeBitsPerBlock(stream.readByte());
		short[] palette = ChunkConstants.GLOBAL_PALETTE;
		if (bitsPerBlock != ChunkConstants.GLOBAL_PALETTE_BITS_PER_BLOCK) {
			palette = new short[VarNumberCodec.readVarInt(stream)];
			for (int i = 0; i < palette.length; i++) {
				palette[i] = (short) VarNumberCodec.readVarInt(stream);
			}
		}
		long[] blockdata = new long[VarNumberCodec.readVarInt(stream)];
		for (int i = 0; i < blockdata.length; i++) {
			blockdata[i] = stream.readLong();
		}
		return new ChunkSectonBlockData(nonAirBlockCount, palette, bitsPerBlock, blockdata);
	}

	protected static byte normalizeBitsPerBlock(byte bitsPerBlock) {
		if (bitsPerBlock <= 4) {
			return 4;
		} else if (bitsPerBlock <= 8) {
			return bitsPerBlock;
		} else {
			return ChunkConstants.GLOBAL_PALETTE_BITS_PER_BLOCK;
		}
	}

	protected final short nonAirBlockCount;
	protected final short[] palette;

	public ChunkSectonBlockData(short nonAirBlockCount, short[] palette, int bitsPerBlock, long[] blockdata) {
		super(bitsPerBlock, blockdata);
		this.nonAirBlockCount = nonAirBlockCount;
		this.palette = palette;
	}

	public int getNonAirBlockCount() {
		return nonAirBlockCount;
	}

	public short[] getPalette() {
		return palette;
	}

	public short getBlockData(int blockindex) {
		return palette[getNumber(blockindex)];
	}

}