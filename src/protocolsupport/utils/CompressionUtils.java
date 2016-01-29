package protocolsupport.utils;

import java.util.Arrays;
import java.util.zip.Deflater;

import protocolsupport.ProtocolSupport;
import protocolsupport.utils.Utils.Converter;

public class CompressionUtils {

	public static void init() {
		ProtocolSupport.logInfo("Compression level: "+compressionLevel);
	}

	private static final int compressionLevel = Utils.getJavaPropertyValue("protocolsupport.compressionlevel", 1, Converter.STRING_TO_INT);

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
