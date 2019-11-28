package protocolsupport.protocol.packet;

import java.util.function.ObjIntConsumer;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.util.Recycler.Handle;

public abstract class PacketData<T extends PacketData<T>> extends UnpooledHeapByteBuf {

	protected static final int HEAD_SPACE_MAX = 10;

	public static final ByteBufAllocator ALLOCATOR = new UnpooledByteBufAllocator(false);

	protected final Handle<T> handle;
	protected PacketData(Handle<T> handle) {
		super(ALLOCATOR, 1024, Integer.MAX_VALUE);
		this.handle = handle;
	}

	@SuppressWarnings("unchecked")
	protected T init(PacketType packetType) {
		this.packetType = packetType;
		this.writeZero(HEAD_SPACE_MAX);
		this.readerIndex(writerIndex());
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public void writeHeadSpace(int length, int value, ObjIntConsumer<T> writer) {

		int newIndex = readerIndex() - length;
		readerIndex(newIndex);

		int writerIndex = writerIndex();

		writerIndex(newIndex);
		writer.accept((T) this, value);

		writerIndex(writerIndex);
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
		clone.init(packetType);
		markReaderIndex();
		clone.writeBytes(this);
		resetReaderIndex();
		return clone;
	}

}
