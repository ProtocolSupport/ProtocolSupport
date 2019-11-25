package protocolsupport.protocol.packet;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.util.Recycler.Handle;

public abstract class PacketData<T extends PacketData<T>> extends UnpooledHeapByteBuf {

	public static final ByteBufAllocator ALLOCATOR = new UnpooledByteBufAllocator(false);

	protected final Handle<T> handle;
	protected PacketData(Handle<T> handle) {
		super(ALLOCATOR, 1024, Integer.MAX_VALUE);
		this.handle = handle;
	}

	protected PacketType packetType;

	public PacketType getPacketType() {
		return packetType;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void deallocate() {
		clear();
		setRefCnt(1);
		handle.recycle((T) this);
	}

	protected abstract T newInstance();

	@Override
	public T clone() {
		T clone = newInstance();
		clone.packetType = packetType;
		markReaderIndex();
		clone.writeBytes(this);
		resetReaderIndex();
		return clone;
	}

}
