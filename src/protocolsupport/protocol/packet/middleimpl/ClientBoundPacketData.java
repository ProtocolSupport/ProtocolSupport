package protocolsupport.protocol.packet.middleimpl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.utils.recyclable.Recyclable;

public class ClientBoundPacketData extends UnpooledHeapByteBuf implements Recyclable, IPacketData {

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
		super(ALLOCATOR, 1024, Integer.MAX_VALUE);
		this.handle = handle;
	}

	private PacketType packetType;

	@Override
	public void recycle() {
		clear();
		packetType = null;
		handle.recycle(this);
	}

	@Override
	public PacketType getPacketType() {
		return packetType;
	}

	@Override
	public int getDataLength() {
		return readableBytes();
	}

	@Override
	public void writeData(ByteBuf to) {
		to.writeBytes(this);
	}

}
