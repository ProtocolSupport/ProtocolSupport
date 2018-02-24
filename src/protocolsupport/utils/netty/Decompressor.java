package protocolsupport.utils.netty;

import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

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

	private final Inflater inflater = new Inflater();
	private final Handle<Decompressor> handle;
	protected Decompressor(Handle<Decompressor> handle) {
		this.handle = handle;
	}

	public byte[] decompress(byte[] input, int uncompressedlength) throws DataFormatException {
		byte[] uncompressed = new byte[uncompressedlength];
		try {
			inflater.setInput(input);
			inflater.inflate(uncompressed);
		} finally {
			inflater.reset();
		}
		return uncompressed;
	}

	public void recycle() {
		handle.recycle(this);
	}

	public static byte[] decompressStatic(byte[] input, int uncompressedlength) throws DataFormatException {
		Decompressor decompressor = create();
		try {
			return decompressor.decompress(input, uncompressedlength);
		} finally {
			decompressor.recycle();
		}
	}

}
