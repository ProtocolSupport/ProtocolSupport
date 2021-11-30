package protocolsupport.protocol.types.chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;

public sealed interface IPalettedStorage permits PalettedStorage, PalettedStorageGlobal, PalettedStorageSingle {

	public static IPalettedStorage decode(ByteBuf data, byte bitsMin, byte bitsMax, byte bitsGlobal) {
		int bitsPerNumber = data.readUnsignedByte();
		if (bitsPerNumber == 0) {
			int id = VarNumberCodec.readVarInt(data);
			if (VarNumberCodec.readVarInt(data) != 0) {
				throw new IllegalArgumentException("Single value paletted storage array should be empty");
			}
			return new PalettedStorageSingle(id);
		} else if (bitsPerNumber <= bitsMax) {
			if (bitsPerNumber < bitsMin) {
				bitsPerNumber = bitsMin;
			}
			short[] palette = new short[VarNumberCodec.readVarInt(data)];
			for (int i = 0; i < palette.length; i++) {
				palette[i] = (short) VarNumberCodec.readVarInt(data);
			}
			long[] blockdata = ArrayCodec.readVarIntLongArray(data);
			return new PalettedStorage(palette, bitsPerNumber, blockdata);
		} else {
			long[] blockdata = ArrayCodec.readVarIntLongArray(data);
			return new PalettedStorageGlobal(bitsGlobal, blockdata);
		}
	}

	public int getBitsPerNumber();

	public int getId(int index);

}
