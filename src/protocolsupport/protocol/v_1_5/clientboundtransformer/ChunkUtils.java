package protocolsupport.protocol.v_1_5.clientboundtransformer;

import protocolsupport.protocol.v_1_5.remappers.BlockIDRemapper;

public class ChunkUtils {

	public static int calcDataSize(final int bitmap, final boolean light, final boolean sendBiomes) {
		final int j = bitmap * 2 * 16 * 16 * 16;
		final int k = (bitmap * 16 * 16 * 16) / 2;
		final int l = light ? ((bitmap * 16 * 16 * 16) / 2) : 0;
		final int i2 = sendBiomes ? 256 : 0;
		return j + k + l + i2;
	}

	public static byte[] to15ChunkData(byte[] data18, int bitmap) {
		int count = 0;
		for (int i = 0; i < 16; i++) {
			if ((bitmap & (1 << i)) != 0) {
				count++;
			}
		}
		byte[] newdata = new byte[(count * (4096 + 2048)) + (data18.length - (count * 8192))];
		int tIndex = 0;
		int mIndex = count * 4096;
		for (int i = 0; i < (8192 * count); i += 2) {
			int state = ((data18[i + 1] & 0xFF) << 8) | (data18[i] & 0xFF);
			newdata[tIndex] = (byte) BlockIDRemapper.replaceBlockId(state >> 4);
			byte data = (byte) (state & 0xF);
			if ((tIndex & 1) == 0) {
				newdata[mIndex] = data;
			} else {
				newdata[mIndex] |= (data << 4);
			}
			if ((tIndex & 1) == 1) {
				mIndex++;
			}
			tIndex++;
		}
		System.arraycopy(data18, 8192 * count, newdata, count * (4096 + 2048), data18.length - (8192 * count));
		return newdata;
	}

}
