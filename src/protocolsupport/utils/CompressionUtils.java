package protocolsupport.utils;

import java.util.Arrays;
import java.util.zip.Deflater;

public class CompressionUtils {

	private static final int compressionLevel = getCompressionLevel();

	private static int getCompressionLevel() {
		try {
			return Integer.parseInt(System.getProperty("protocolsupport.compressionlevel", "1"));
		} catch (Throwable t) {
		}
		return 1;
	}

	public static byte[] compress(byte[] input) {
		Deflater deflater = new Deflater(compressionLevel);
		try {
			deflater.setInput(input);
			deflater.finish();
			byte[] compressedBuf = new byte[input.length * 11 / 10 + 6];
			int size = deflater.deflate(compressedBuf);
			return Arrays.copyOf(compressedBuf, size);
		} finally {
			deflater.end();
		}
	}

}
