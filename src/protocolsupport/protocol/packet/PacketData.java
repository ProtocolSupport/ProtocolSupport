package protocolsupport.protocol.packet;

import java.text.MessageFormat;
import java.util.function.ObjIntConsumer;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.buffer.UnpooledHeapByteBuf;
import protocolsupport.ProtocolSupport;
import protocolsupport.ProtocolSupportFileLog;
import protocolsupport.utils.JavaSystemProperty;
import protocolsupport.utils.ThreadLocalObjectPool;
import protocolsupport.zplatform.ServerPlatform;

public abstract class PacketData<PT, PD extends PacketData<PT, PD>> extends UnpooledHeapByteBuf {

	protected static final int MAX_POOL_CAPACITY_TOTAL = JavaSystemProperty.getValue("packetdatapool.capacity", 200, Integer::parseInt);
	protected static final int MAX_POOL_CAPACITY;

	static {
		int ioThreadCount = ServerPlatform.get().getMiscUtils().getServerIOEventLoopGroup().executorCount();
		int maxPooledPacketDataObjects = MAX_POOL_CAPACITY_TOTAL / ioThreadCount;
		if (maxPooledPacketDataObjects < 2) {
			maxPooledPacketDataObjects = 2;
		}
		MAX_POOL_CAPACITY = maxPooledPacketDataObjects;
		String message = MessageFormat.format("Packet data pool per io thread capacity: {0} (total ~{1} spread to {2} io threads)", MAX_POOL_CAPACITY, MAX_POOL_CAPACITY_TOTAL, ioThreadCount);
		ProtocolSupport.logInfo(message);
		if (ProtocolSupportFileLog.isEnabled()) {
			ProtocolSupportFileLog.logInfoMessage(message);
		}
	}

	protected static final int HEAD_SPACE_MAX = 10;

	public static final ByteBufAllocator ALLOCATOR = new UnpooledByteBufAllocator(false);

	protected final ThreadLocalObjectPool.Handle<PD> handle;

	protected PacketData(ThreadLocalObjectPool.Handle<PD> handle) {
		super(ALLOCATOR, 1024, Integer.MAX_VALUE);
		this.handle = handle;
	}

	@SuppressWarnings("unchecked")
	protected PD init(PT packetType) {
		this.packetType = packetType;
		this.writeZero(HEAD_SPACE_MAX);
		this.readerIndex(writerIndex());
		return (PD) this;
	}

	@SuppressWarnings("unchecked")
	public void writeHeadSpace(int length, int value, ObjIntConsumer<PD> writer) {

		int newIndex = readerIndex() - length;
		readerIndex(newIndex);

		int writerIndex = writerIndex();

		writerIndex(newIndex);
		writer.accept((PD) this, value);

		writerIndex(writerIndex);
	}

	protected PT packetType;

	public PT getPacketType() {
		return packetType;
	}

	@Override
	protected void deallocate() {
		clear();
		setRefCnt(1);
		handle.recycle();
	}

	@Override
	public abstract PD clone();

}
