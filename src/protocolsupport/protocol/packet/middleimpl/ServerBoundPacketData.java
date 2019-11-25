package protocolsupport.protocol.packet.middleimpl;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ServerBoundPacketData extends PacketData<ServerBoundPacketData> {

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
	protected ServerBoundPacketData newInstance() {
		return recycler.get();
	}

	public static ServerBoundPacketData create(PacketType packetType) {
		return recycler.get().init(packetType);
	}

	protected ServerBoundPacketData init(PacketType packetType) {
		this.packetType = packetType;
		VarNumberSerializer.writeVarInt(this, packetType.getId());
		return this;
	}

}
