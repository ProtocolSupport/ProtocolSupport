package protocolsupport.protocol.types.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.NumberBitsStoragePadded;

public class ChunkSectonBlockData extends NumberBitsStoragePadded {

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