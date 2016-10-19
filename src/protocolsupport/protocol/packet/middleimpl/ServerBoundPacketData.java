package protocolsupport.protocol.packet.middleimpl;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.util.Recycler;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.serializer.ChainedProtocolSupportPacketDataSerializer;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.recyclable.Recyclable;

public class ServerBoundPacketData extends ChainedProtocolSupportPacketDataSerializer implements Recyclable {

	private static final Recycler<ServerBoundPacketData> RECYCLER = new Recycler<ServerBoundPacketData>() {
		@Override
		protected ServerBoundPacketData newObject(Recycler.Handle handle) {
			return new ServerBoundPacketData(handle);
		}
	};

	public static ServerBoundPacketData create(ServerBoundPacket packet) {
		ServerBoundPacketData packetdata = RECYCLER.get();
		packetdata.packet = packet;
		ChannelUtils.writeVarInt(packetdata, packet.getId());
		return packetdata;
	}

	private final Recycler.Handle handle;
	private ServerBoundPacketData(Recycler.Handle handle) {
		this.handle = handle;
	}

	private ServerBoundPacket packet;

	public ServerBoundPacket getPacketType() {
		return packet;
	}

	@Override
	public void setBuf(ByteBuf buf) {
	}

	@Override
	protected void finalize() {
		release();
	}

	@Override
	public void close() throws IOException {
		recycle();
	}

	@Override
	public void recycle() {
		clear();
		packet = null;
		RECYCLER.recycle(this, handle);
	}

}
