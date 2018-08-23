package protocolsupport.protocol.packet.middleimpl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.utils.netty.WrappingBuffer;
import protocolsupport.utils.recyclable.Recyclable;

public class ServerBoundPacketData extends WrappingBuffer implements Recyclable {

	protected static final Recycler<ServerBoundPacketData> recycler = new Recycler<ServerBoundPacketData>() {
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
		super(Unpooled.buffer());
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
	public void recycle() {
		clear();
		packet = null;
		handle.recycle(this);
	}

	@Override
	public void setBuf(ByteBuf buf) {
	}

}
