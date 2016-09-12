package protocolsupport.protocol.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.util.Recycler;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.utils.netty.Allocator;

public class RecyclableProtocolSupportPacketDataSerializer extends ProtocolSupportPacketDataSerializer {

	private static final Recycler<RecyclableProtocolSupportPacketDataSerializer> RECYCLER = new Recycler<RecyclableProtocolSupportPacketDataSerializer>() {
		@Override
		protected RecyclableProtocolSupportPacketDataSerializer newObject(Recycler.Handle handle) {
			return new RecyclableProtocolSupportPacketDataSerializer(handle);
		}
	};

	public static RecyclableProtocolSupportPacketDataSerializer create(ProtocolVersion version) {
		RecyclableProtocolSupportPacketDataSerializer serializer = RECYCLER.get();
		serializer.version = version;
		return serializer;
	}

	private final Recycler.Handle handle;
	private RecyclableProtocolSupportPacketDataSerializer(Recycler.Handle handle) {
		super(Allocator.allocateUnpooledBuffer(), null);
		this.handle = handle;
	}

	@Override
	public void setBuf(ByteBuf buf) {
	}

	@Override
	public boolean release(int count) {
		return release();
	}

	@Override
	public boolean release() {
		recycle();
		return true;
	}

	private void recycle() {
		clear();
		RECYCLER.recycle(this, handle);
	}

	@Override
	protected void finalize() {
		super.release();
	}

}
