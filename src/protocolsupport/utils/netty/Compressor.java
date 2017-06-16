package protocolsupport.utils.netty;

import java.util.Arrays;
import java.util.zip.Deflater;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupport.ProtocolSupport;
import protocolsupport.utils.Utils;

public class Compressor {

	private static final int compressionLevel = Utils.getJavaPropertyValue("compressionlevel", 3, Integer::parseInt);

	static {
		ProtocolSupport.logInfo("Compression level: "+compressionLevel);
	}

	private static final Recycler<Compressor> recycler = new Recycler<Compressor>() {
		@Override
		protected Compressor newObject(Handle<Compressor> handle) {
			return new Compressor(handle);
		}
	};

	public static Compressor create() {
		return recycler.get();
	}

	private final Deflater deflater = new Deflater(compressionLevel);
	private final Handle<Compressor> handle;
	protected Compressor(Handle<Compressor> handle) {
		this.handle = handle;
	}

	public byte[] compress(byte[] input) {
		deflater.setInput(input);
		deflater.finish();
		byte[] compressedBuf = new byte[((input.length * 11) / 10) + 50];
		int size = deflater.deflate(compressedBuf);
		deflater.reset();
		return Arrays.copyOf(compressedBuf, size);
	}

	public void recycle() {
		handle.recycle(this);
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
