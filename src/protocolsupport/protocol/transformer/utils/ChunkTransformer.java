package protocolsupport.protocol.transformer.utils;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable;

public class ChunkTransformer {

	public static int calcDataSize(final int count, final boolean light, final boolean sendBiomes) {
		int idlength = count * 2 * 16 * 16 * 16;
		int blocklightlength = (count * 16 * 16 * 16) / 2;
		int skylightlength = light ? ((count * 16 * 16 * 16) / 2) : 0;
		int biomeslength = sendBiomes ? 256 : 0;
		return idlength + blocklightlength + skylightlength + biomeslength;
	}

	public static byte[] toPre18Data(byte[] data18, int bitmap, ProtocolVersion version) {
		int count = Integer.bitCount(bitmap);
		byte[] newdata = new byte[(count * (4096 + 2048)) + (data18.length - (count * 8192))];
		int tIndex = 0;
		int mIndex = count * 4096;
		RemappingTable table = IdRemapper.BLOCK.getTable(version);
		for (int i = 0; i < (8192 * count); i += 2) {
			int state = ((data18[i + 1] & 0xFF) << 8) | (data18[i] & 0xFF);
			newdata[tIndex] = (byte) table.getRemap(state >> 4);
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
