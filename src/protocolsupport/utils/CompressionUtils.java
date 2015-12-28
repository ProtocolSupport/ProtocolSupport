package protocolsupport.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.commons.io.IOUtils;

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

	public static ByteBuf uncompress(ByteBuf payload) throws IOException {
		return Unpooled.wrappedBuffer(IOUtils.toByteArray(new InflaterInputStream(new ByteBufInputStream(payload), new Inflater(), 8192)));
	}

}
