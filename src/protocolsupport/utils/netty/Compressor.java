package protocolsupport.utils.netty;

import java.util.Arrays;
import java.util.zip.Deflater;

import io.netty.buffer.ByteBuf;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupport.ProtocolSupport;
import protocolsupport.utils.JavaSystemProperty;
import protocolsupportbuildprocessor.Preload;

@Preload
public class Compressor {

	protected static final int compressionLevel = JavaSystemProperty.getValue("compressionlevel", 3, Integer::parseInt);

	static {
		ProtocolSupport.logInfo("Compression level: " + compressionLevel);
	}

	protected static final Recycler<Compressor> recycler = new Recycler<Compressor>() {
		@Override
		protected Compressor newObject(Handle<Compressor> handle) {
			return new Compressor(handle);
		}
	};

	public static Compressor create() {
		return recycler.get();
	}

	protected final Deflater deflater = new Deflater(compressionLevel);
	protected final Handle<Compressor> handle;
	protected Compressor(Handle<Compressor> handle) {
		this.handle = handle;
	}

	protected final ReusableWriteHeapBuffer writeBuffer = new ReusableWriteHeapBuffer();

	protected int getMaxCompressedSize(int uncompressedSize) {
		return ((uncompressedSize * 11) / 10) + 50;
	}

	public byte[] compress(byte[] input, int offset, int length) {
		deflater.setInput(input, offset, length);
		deflater.finish();
		byte[] compressedBuf = writeBuffer.getBuffer(getMaxCompressedSize(length));
		int size = deflater.deflate(compressedBuf);
		deflater.reset();
		return Arrays.copyOf(compressedBuf, size);
	}

	public void compressTo(ByteBuf to, byte[] input, int offset, int length) throws Exception {
		deflater.setInput(input, offset, length);
		deflater.finish();
		writeBuffer.writeTo(to, getMaxCompressedSize(length), deflater::deflate);
		deflater.reset();
	}

	public void recycle() {
		deflater.reset();
		handle.recycle(this);
	}

	public static byte[] compressStatic(byte[] input) {
		Compressor compressor = create();
		try {
			return compressor.compress(input, 0, input.length);
		} finally {
			compressor.recycle();
		}
	}

}
