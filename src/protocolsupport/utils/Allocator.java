package protocolsupport.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;

public class Allocator {

	public static void init() {
	}

	private static final boolean direct = getDirect();
	private static final ByteBufAllocator allocator = getAllocator();

	private static final boolean getDirect() {
		String direct = System.getProperty("protocolsupport.buffer", "direct");
		switch (direct.toLowerCase()) {
			case "direct": {
				return true;
			}
			case "heap": {
				return false;
			}
			default: {
				return true;
			}
		}
	}

	private static final ByteBufAllocator getAllocator() {
		String allocator = System.getProperty("protocolsupport.allocator", "pooled");
		switch (allocator.toLowerCase()) {
			case "pooled": {
				return PooledByteBufAllocator.DEFAULT;
			}
			case "unpooled": {
				return UnpooledByteBufAllocator.DEFAULT;
			}
			default: {
				return PooledByteBufAllocator.DEFAULT;
			}
		}
	}

	public static ByteBuf allocateBuffer() {
		if (direct) {
			return allocator.directBuffer();
		} else {
			return allocator.heapBuffer();
		}
	}

}
