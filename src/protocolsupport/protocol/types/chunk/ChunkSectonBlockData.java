package protocolsupport.protocol.types.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ChunkSectonBlockData {

	public static ChunkSectonBlockData readFromStream(ByteBuf stream) {
		short nonAirBlockCount = stream.readShort();
		byte bitsPerBlock = stream.readByte();
		short[] palette = ChunkConstants.GLOBAL_PALETTE;
		if (bitsPerBlock != ChunkConstants.GLOBAL_PALETTE_BITS_PER_BLOCK) {
			palette = new short[VarNumberSerializer.readVarInt(stream)];
			for (int i = 0; i < palette.length; i++) {
				palette[i] = (short) VarNumberSerializer.readVarInt(stream);
			}
		}
		long[] blockdata = new long[VarNumberSerializer.readVarInt(stream)];
		for (int i = 0; i < blockdata.length; i++) {
			blockdata[i] = stream.readLong();
		}
		return new ChunkSectonBlockData(nonAirBlockCount, palette, bitsPerBlock, blockdata);
	}

	protected final short nonAirBlockCount;
	protected final short[] palette;
	protected final long[] blockdata;
	protected final int bitsPerBlock;
	private final int singleValMask;

	public ChunkSectonBlockData(short nonAirBlockCount, short[] palette, int bitsPerBlock, long[] blockdata) {
		this.nonAirBlockCount = nonAirBlockCount;
		this.palette = palette;
		this.blockdata = blockdata;
		this.bitsPerBlock = bitsPerBlock;
		this.singleValMask = (1 << bitsPerBlock) - 1;
	}

	public int getNonAirBlockCount() {
		return nonAirBlockCount;
	}

	public int getBitsPerBlock() {
		return bitsPerBlock;
	}

	public short[] getPalette() {
		return palette;
	}

	public long[] getBlockData() {
		return blockdata;
	}

	public short getBlockData(int blockindex) {
		return palette[getRuntimeId(blockindex)];
	}

	public short getRuntimeId(int blockIndex) {
		int bitStartIndex = blockIndex * bitsPerBlock;
		int arrStartIndex = bitStartIndex >> 6;
		int arrEndIndex = ((bitStartIndex + bitsPerBlock) - 1) >> 6;
		int localStartBitIndex = bitStartIndex & 63;
		if (arrStartIndex == arrEndIndex) {
			return (short) ((this.blockdata[arrStartIndex] >>> localStartBitIndex) & this.singleValMask);
		} else {
			return (short) (((this.blockdata[arrStartIndex] >>> localStartBitIndex) | (this.blockdata[arrEndIndex] << (64 - localStartBitIndex))) & this.singleValMask);
		}
	}

}