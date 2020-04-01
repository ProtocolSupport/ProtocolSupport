package protocolsupport.protocol.packet.middleimpl;

import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.utils.ThreadLocalObjectPool;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ServerBoundPacketData extends PacketData<ServerBoundPacketData> {

	protected static final ThreadLocalObjectPool<ServerBoundPacketData> pool = new ThreadLocalObjectPool<>(MAX_POOL_CAPACITY, ServerBoundPacketData::new);

	public static ServerBoundPacketData create(PacketType packetType) {
		return pool.get().init(packetType);
	}

	protected ServerBoundPacketData(ThreadLocalObjectPool.Handle<ServerBoundPacketData> handle) {
		super(handle);
	}

	@Override
	public ServerBoundPacketData clone() {
		ServerBoundPacketData clone = pool.get().init(packetType);
		getBytes(readerIndex(), clone);
		return clone;
	}

}
