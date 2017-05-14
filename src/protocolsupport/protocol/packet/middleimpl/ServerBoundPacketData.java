package protocolsupport.protocol.packet.middleimpl;

import io.netty.buffer.ByteBuf;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.WrappingBuffer;
import protocolsupport.utils.recyclable.Recyclable;

public class ServerBoundPacketData extends WrappingBuffer implements Recyclable {

	private static final Recycler<ServerBoundPacketData> recycler = new Recycler<ServerBoundPacketData>() {
		@Override
		protected ServerBoundPacketData newObject(Handle<ServerBoundPacketData> handle) {
			return new ServerBoundPacketData(handle);
		}
	};

	public static ServerBoundPacketData create(ServerBoundPacket packet) {
		ServerBoundPacketData packetdata = recycler.get();
		packetdata.packet = packet;
		return packetdata;
	}

	private final Handle<ServerBoundPacketData> handle;
	private ServerBoundPacketData(Handle<ServerBoundPacketData> handle) {
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
		handle.recycle(this);
	}

}
