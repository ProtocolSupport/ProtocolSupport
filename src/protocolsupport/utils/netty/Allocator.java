package protocolsupport.utils.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import protocolsupport.ProtocolSupport;
import protocolsupport.utils.Utils;
import protocolsupport.utils.Utils.Converter;

public class Allocator {

	public static void init() {
		ProtocolSupport.logInfo("Allocator: "+allocator + ", direct: "+direct);
	}

	private static final boolean direct = Utils.getJavaPropertyValue("protocolsupport.buffer", true, new Converter<String, Boolean>() {
		@Override
		public Boolean convert(String t) {
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
		}
	});
	private static final ByteBufAllocator allocator = Utils.getJavaPropertyValue("protocolsupport.allocator", PooledByteBufAllocator.DEFAULT, new Converter<String, ByteBufAllocator>() {
		@Override
		public ByteBufAllocator convert(String t) {
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
		}
	});

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
