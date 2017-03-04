package protocolsupport.protocol.packet.middleimpl;

import io.netty.buffer.ByteBuf;
import io.netty.util.Recycler;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.WrappingBuffer;
import protocolsupport.utils.recyclable.Recyclable;

public class ServerBoundPacketData extends WrappingBuffer implements Recyclable {

	private static final Recycler<ServerBoundPacketData> RECYCLER = new Recycler<ServerBoundPacketData>() {
		@Override
		protected ServerBoundPacketData newObject(Recycler.Handle handle) {
			return new ServerBoundPacketData(handle);
		}
	};

	public static ServerBoundPacketData create(ServerBoundPacket packet) {
		ServerBoundPacketData packetdata = RECYCLER.get();
		packetdata.packet = packet;
		return packetdata;
	}

	private final Recycler.Handle handle;
	private ServerBoundPacketData(Recycler.Handle handle) {
		super(Allocator.allocateUnpooledBuffer());
		this.handle = handle;
	}

	private ServerBoundPacket packet;

	public ServerBoundPacket getPacketType() {
		return packet;
	}

	public int getPacketId() {
		return packet.getId();
	}

	@Override
	public void setBuf(ByteBuf buf) {
	}

	@Override
	protected void finalize() {
		release();
	}

	@Override
	public void recycle() {
		clear();
		packet = null;
		RECYCLER.recycle(this, handle);
	}

}
