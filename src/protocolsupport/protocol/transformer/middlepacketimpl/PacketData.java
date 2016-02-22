package protocolsupport.protocol.transformer.middlepacketimpl;

import io.netty.util.Recycler;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.netty.Allocator;

public class PacketData extends PacketDataSerializer {

	private static final Recycler<PacketData> RECYCLER = new Recycler<PacketData>() {
		@Override
		protected PacketData newObject(Recycler.Handle handle) {
			return new PacketData(handle);
		}
	};

	public static PacketData create(int packetId, ProtocolVersion version) {
		PacketData packetdata = RECYCLER.get();
		packetdata.packetId = packetId;
		packetdata.setVersion(version);
		return packetdata;
	}

	private final Recycler.Handle handle;
	private PacketData(Recycler.Handle handle) {
		super(Allocator.allocateUnpooledBuffer());
		this.handle = handle;
	}

	public void recycle() {
		packetId = 0;
		clear();
		RECYCLER.recycle(this, handle);
	}

	private int packetId;

	public int getPacketId() {
		return packetId;
	}

	@Override
	protected void finalize() {
		release();
	}

}
