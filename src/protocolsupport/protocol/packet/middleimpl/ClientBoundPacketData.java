package protocolsupport.protocol.packet.middleimpl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.utils.netty.WrappingBuffer;
import protocolsupport.utils.recyclable.Recyclable;

public class ClientBoundPacketData extends WrappingBuffer implements Recyclable, IPacketData {

	protected static final Recycler<ClientBoundPacketData> recycler = new Recycler<ClientBoundPacketData>() {
		@Override
		protected ClientBoundPacketData newObject(Handle<ClientBoundPacketData> handle) {
			return new ClientBoundPacketData(handle);
		}
	};

	public static ClientBoundPacketData create(PacketType packetType) {
		ClientBoundPacketData packetdata = recycler.get();
		packetdata.packetType = packetType;
		return packetdata;
	}

	private final Handle<ClientBoundPacketData> handle;
	private ClientBoundPacketData(Handle<ClientBoundPacketData> handle) {
		super(Unpooled.buffer());
		this.handle = handle;
	}

	private PacketType packetType;

	@Override
	public void recycle() {
		packetType = null;
		buf.clear();
		handle.recycle(this);
	}

	@Override
	public void setBuf(ByteBuf buf) {
	}

	@Override
	public PacketType getPacketType() {
		return packetType;
	}

	@Override
	public int getDataLength() {
		return buf.readableBytes();
	}

	@Override
	public void writeData(ByteBuf to) {
		to.writeBytes(buf);
	}

}
