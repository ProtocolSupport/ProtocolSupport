package protocolsupport.utils.netty;

import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import io.netty.buffer.ByteBuf;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;

public class Decompressor {

	protected static final Recycler<Decompressor> recycler = new Recycler<Decompressor>() {
		@Override
		protected Decompressor newObject(Handle<Decompressor> handle) {
			return new Decompressor(handle);
		}
	};

	public static Decompressor create() {
		return recycler.get();
	}

	protected final Inflater inflater = new Inflater();
	protected final Handle<Decompressor> handle;
	protected Decompressor(Handle<Decompressor> handle) {
		this.handle = handle;
	}

	public byte[] decompress(byte[] input, int offset, int length, int uncompressedlength) throws DataFormatException {
		inflater.setInput(input, offset, length);
		byte[] uncompressed = new byte[uncompressedlength];
		inflater.inflate(uncompressed);
		inflater.reset();
		return uncompressed;
	}

	protected final ReusableWriteHeapBuffer writerBuffer = new ReusableWriteHeapBuffer();

	public void decompressTo(ByteBuf to, byte[] input, int offset, int length, int uncompressedlength) throws Exception {
		inflater.setInput(input, offset, length);
		writerBuffer.writeTo(to, uncompressedlength, inflater::inflate);
		inflater.reset();
	}

	public void recycle() {
		inflater.reset();
		handle.recycle(this);
	}

	public static byte[] decompressStatic(byte[] input, int uncompressedlength) throws DataFormatException {
		Decompressor decompressor = create();
		try {
			return decompressor.decompress(input, 0, input.length, uncompressedlength);
		} finally {
			decompressor.recycle();
		}
	}

}
