package protocolsupport.utils.netty;

import java.util.Arrays;
import java.util.zip.Deflater;

import io.netty.buffer.ByteBuf;
import protocolsupport.ProtocolSupport;
import protocolsupport.utils.JavaSystemProperty;

public class Compressor {

	protected static final int compressionLevel = JavaSystemProperty.getValue("compressionlevel", 3, Integer::parseInt);

	static {
		ProtocolSupport.logInfo("Compression level: " + compressionLevel);
	}

	protected final Deflater deflater;

	public Compressor(Deflater deflater) {
		this.deflater = deflater;
	}

	public Compressor(int level, boolean nowrap) {
		this(new Deflater(level, nowrap));
	}

	public Compressor(boolean nowrap) {
		this(compressionLevel, nowrap);
	}

	public Compressor() {
		this(compressionLevel, false);
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

}
