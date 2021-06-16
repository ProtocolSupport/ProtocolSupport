package protocolsupport.utils.netty;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupportbuildprocessor.Preload;

@Preload
public class RecyclableWrapCompressor extends Compressor {

	protected static final Recycler<RecyclableWrapCompressor> recycler = new Recycler<>() {
		@Override
		protected RecyclableWrapCompressor newObject(Handle<RecyclableWrapCompressor> handle) {
			return new RecyclableWrapCompressor(handle);
		}
	};

	public static RecyclableWrapCompressor create() {
		return recycler.get();
	}

	protected final Handle<RecyclableWrapCompressor> handle;

	protected RecyclableWrapCompressor(Handle<RecyclableWrapCompressor> handle) {
		super(false);
		this.handle = handle;
	}

	public void recycle() {
		deflater.reset();
		handle.recycle(this);
	}

	public static byte[] compressStatic(byte[] input) {
		RecyclableWrapCompressor compressor = create();
		try {
			return compressor.compress(input, 0, input.length);
		} finally {
			compressor.recycle();
		}
	}

}
