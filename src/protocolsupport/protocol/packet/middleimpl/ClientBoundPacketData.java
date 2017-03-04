package protocolsupport.protocol.packet.middleimpl;

import io.netty.buffer.ByteBuf;
import io.netty.util.Recycler;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.WrappingBuffer;
import protocolsupport.utils.recyclable.Recyclable;

public class ClientBoundPacketData extends WrappingBuffer implements Recyclable {

	private static final Recycler<ClientBoundPacketData> RECYCLER = new Recycler<ClientBoundPacketData>() {
		@Override
		protected ClientBoundPacketData newObject(Recycler.Handle handle) {
			return new ClientBoundPacketData(handle);
		}
	};

	public static ClientBoundPacketData create(int packetId, ProtocolVersion version) {
		ClientBoundPacketData packetdata = RECYCLER.get();
		packetdata.packetId = packetId;
		return packetdata;
	}

	private final Recycler.Handle handle;
	private ClientBoundPacketData(Recycler.Handle handle) {
		super(Allocator.allocateUnpooledBuffer());
		this.handle = handle;
	}

	@Override
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
	public void setBuf(ByteBuf buf) {
	}

	@Override
	protected void finalize() {
		release();
	}

}
