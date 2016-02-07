package protocolsupport.utils.netty;

import java.util.Arrays;
import java.util.zip.Deflater;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupport.ProtocolSupport;
import protocolsupport.utils.Utils;
import protocolsupport.utils.Utils.Converter;

public class Compressor {

	public static void init() {
		ProtocolSupport.logInfo("Compression level: "+compressionLevel);
	}

	private static final int compressionLevel = Utils.getJavaPropertyValue("protocolsupport.compressionlevel", 3, Converter.STRING_TO_INT);

	private static final Recycler<Compressor> recycler = new Recycler<Compressor>() {
		@Override
		protected Compressor newObject(Handle handle) {
			return new Compressor(handle);
		}
	};

	public static Compressor create() {
		return recycler.get();
	}

	private final Deflater deflater = new Deflater(compressionLevel);
	private final Handle handle;
	protected Compressor(Handle handle) {
		this.handle = handle;
	}

	public byte[] compress(byte[] input) {
		deflater.setInput(input);
		deflater.finish();
		byte[] compressedBuf = new byte[input.length * 11 / 10 + 6];
		int size = deflater.deflate(compressedBuf);
		deflater.reset();
		return Arrays.copyOf(compressedBuf, size);
	}

	public void recycle() {
		recycler.recycle(this, handle);
	}

	public static byte[] compressStatic(byte[] input) {
		Compressor compressor = create();
		try {
			return compressor.compress(input);
		} finally {
			compressor.recycle();
		}
	}

}
