package protocolsupport.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.commons.io.IOUtils;

public class CompressionUtils {

	private static final int compressionLevel = getCompressionLevel();

	private static int getCompressionLevel() {
		try {
			return Integer.parseInt(System.getProperty("protocolsupport.compressionlevel", "3"));
		} catch (Throwable t) {
		}
		return 3;
	}

	private static final int compressionBuffer = getCompressionBuffer();

	private static int getCompressionBuffer() {
		try {
			return Integer.parseInt(System.getProperty("protocolsupport.compressionbuffer", "10240"));
		} catch (Throwable t) {
		}
		return 10240;
	}

	public static byte[] compress(byte[] input) throws IOException {
		Deflater deflater = new Deflater(compressionLevel);
		try {
			return IOUtils.toByteArray(new DeflaterInputStream(new ByteArrayInputStream(input), new Deflater(compressionLevel), compressionBuffer));
		} finally {
			deflater.end();
		}
	}

	public static byte[] uncompress(byte[] input) throws IOException {
		Inflater inflater = new Inflater();
		try {
			return IOUtils.toByteArray(new InflaterInputStream(new ByteArrayInputStream(input), inflater, compressionBuffer));
		} finally {
			inflater.end();
		}
	}

}
