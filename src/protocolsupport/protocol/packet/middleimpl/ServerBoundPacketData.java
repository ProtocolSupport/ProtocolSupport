package protocolsupport.protocol.packet.middleimpl;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.packet.PacketType;

public class ServerBoundPacketData extends PacketData<ServerBoundPacketData> {

	public static ServerBoundPacketData create(PacketType packetType) {
		return recycler.get().init(packetType);
	}

	protected static final Recycler<ServerBoundPacketData> recycler = new Recycler<ServerBoundPacketData>() {
		@Override
		protected ServerBoundPacketData newObject(Handle<ServerBoundPacketData> handle) {
			return new ServerBoundPacketData(handle);
		}
	};

	protected ServerBoundPacketData(Handle<ServerBoundPacketData> handle) {
		super(handle);
	}

	@Override
	public ServerBoundPacketData clone() {
		ServerBoundPacketData clone = recycler.get().init(packetType);
		getBytes(readerIndex(), clone);
		return clone;
	}

}
