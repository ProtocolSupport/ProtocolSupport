package protocolsupport.protocol;

import io.netty.util.Recycler;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.utils.netty.Allocator;

public class RecyclablePacketDataSerializer extends PacketDataSerializer {

	private static final Recycler<RecyclablePacketDataSerializer> RECYCLER = new Recycler<RecyclablePacketDataSerializer>() {
		@Override
		protected RecyclablePacketDataSerializer newObject(Recycler.Handle handle) {
			return new RecyclablePacketDataSerializer(handle);
		}
	};

	public static RecyclablePacketDataSerializer create(ProtocolVersion version) {
		RecyclablePacketDataSerializer serializer = RECYCLER.get();
		serializer.setVersion(version);
		return serializer;
	}

	public static RecyclablePacketDataSerializer create(ProtocolVersion version, byte[] data) {
		RecyclablePacketDataSerializer serializer = create(version);
		serializer.writeBytes(data);
		return serializer;
	}

	private final Recycler.Handle handle;
	private RecyclablePacketDataSerializer(Recycler.Handle handle) {
		super(Allocator.allocateUnpooledBuffer());
		this.handle = handle;
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
