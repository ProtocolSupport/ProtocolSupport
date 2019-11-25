package protocolsupport.protocol.packet.middleimpl;

import java.util.function.BiConsumer;

import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.packet.PacketType;

public class ClientBoundPacketData extends PacketData<ClientBoundPacketData> {

	protected static final Recycler<ClientBoundPacketData> recycler = new Recycler<ClientBoundPacketData>() {
		@Override
		protected ClientBoundPacketData newObject(Handle<ClientBoundPacketData> handle) {
			return new ClientBoundPacketData(handle);
		}
	};

	protected ClientBoundPacketData(Handle<ClientBoundPacketData> handle) {
		super(handle);
	}

	@Override
	protected ClientBoundPacketData newInstance() {
		return recycler.get();
	}

	public static ClientBoundPacketData create(PacketType packetType, BiConsumer<ClientBoundPacketData, PacketType> packetIdWriter) {
		return recycler.get().init(packetType, packetIdWriter);
	}

	protected ClientBoundPacketData init(PacketType packetType, BiConsumer<ClientBoundPacketData, PacketType> packetIdWriter) {
		this.packetType = packetType;
		packetIdWriter.accept(this, packetType);
		return this;
	}

}
