package protocolsupport.protocol.packet.middleimpl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupport.utils.netty.WrappingBuffer;
import protocolsupport.utils.recyclable.Recyclable;

public class ClientBoundPacketData extends WrappingBuffer implements Recyclable {

	protected static final Recycler<ClientBoundPacketData> recycler = new Recycler<ClientBoundPacketData>() {
		@Override
		protected ClientBoundPacketData newObject(Handle<ClientBoundPacketData> handle) {
			return new ClientBoundPacketData(handle);
		}
	};

	public static ClientBoundPacketData create(int packetId) {
		ClientBoundPacketData packetdata = recycler.get();
		packetdata.packetId = packetId;
		return packetdata;
	}

	private final Handle<ClientBoundPacketData> handle;
	private ClientBoundPacketData(Handle<ClientBoundPacketData> handle) {
		super(Unpooled.buffer());
		this.handle = handle;
	}

	@Override
	public void recycle() {
		packetId = 0;
		clear();
		handle.recycle(this);
	}

	private int packetId;

	public int getPacketId() {
		return packetId;
	}

	@Override
	public void setBuf(ByteBuf buf) {
	}

}
