package protocolsupport.utils.netty;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import io.netty.handler.codec.DecoderException;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;

public class Decompressor {

	private final Inflater inflater = new Inflater();
	private final byte[] buffer = new byte[2 << 21]; //the maximum size of a pc message (21 bits)
	private final Handle handle;
	protected Decompressor(Handle handle) {
		this.handle = handle;
	}

	private static final Recycler<Decompressor> recycler = new Recycler<Decompressor>() {
		@Override
		protected Decompressor newObject(Handle handle) {
			return new Decompressor(handle);
		}
	};

	public static Decompressor create() {
		return recycler.get();
	}

	public byte[] decompress(byte[] input) {
		inflater.setInput(input);
		try {
			int length = inflater.inflate(buffer);
			if (!inflater.finished()) {
				throw new DecoderException(MessageFormat.format("Badly compressed packet - size is larger than protocol maximum of {0}", (int) Math.pow(2, 7 * 3)));
			}
			return Arrays.copyOf(buffer, length);
		} catch (DataFormatException e) {
			throw new RuntimeException(e);
		} finally {
			inflater.reset();
		}
	}

	public void recycle() {
		recycler.recycle(this, handle);
	}

	public static byte[] decompressStatic(byte[] input) {
		Decompressor decompressor = create();
		try {
			return decompressor.decompress(input);
		} finally {
			decompressor.recycle();
		}
	}

}
