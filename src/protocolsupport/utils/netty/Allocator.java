package protocolsupport.utils.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import protocolsupport.ProtocolSupport;
import protocolsupport.utils.Utils;

public class Allocator {

	private static final boolean direct = Utils.getJavaPropertyValue("buffer", true, (t -> {
		switch (t.toLowerCase()) {
			case "direct": {
				return true;
			}
			case "heap": {
				return false;
			}
			default: {
				throw new RuntimeException("Invalid buffer type "+t.toLowerCase()+", should be either heap or direct");
			}
		}
	}));
	private static final ByteBufAllocator allocator = Utils.getJavaPropertyValue("allocator", PooledByteBufAllocator.DEFAULT, (t -> {
		switch (t.toLowerCase()) {
			case "pooled": {
				return PooledByteBufAllocator.DEFAULT;
			}
			case "unpooled": {
				return UnpooledByteBufAllocator.DEFAULT;
			}
			default: {
				throw new IllegalArgumentException("Invalid allocator type "+t.toLowerCase()+", should be either pooled or unpooled");
			}
		}
	}));

	static {
		ProtocolSupport.logInfo("Allocator: "+allocator + ", direct: "+direct);
	}

	public static ByteBuf allocateBuffer() {
		if (direct) {
			return allocator.directBuffer();
		} else {
			return allocator.heapBuffer();
		}
	}

	public static ByteBuf allocateUnpooledBuffer() {
		if (direct) {
			return UnpooledByteBufAllocator.DEFAULT.directBuffer();
		} else {
			return UnpooledByteBufAllocator.DEFAULT.heapBuffer();
		}
	}

}
