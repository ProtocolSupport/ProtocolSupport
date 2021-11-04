package protocolsupport.protocol.packet;

import protocolsupport.utils.ThreadLocalObjectPool;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ClientBoundPacketData extends PacketData<ClientBoundPacketType, ClientBoundPacketData> {

	protected static final ThreadLocalObjectPool<ClientBoundPacketData> pool = new ThreadLocalObjectPool<>(MAX_POOL_CAPACITY, ClientBoundPacketData::new);

	public static ClientBoundPacketData create(ClientBoundPacketType packetType) {
		return pool.get().init(packetType);
	}

	protected ClientBoundPacketData(ThreadLocalObjectPool.Handle<ClientBoundPacketData> handle) {
		super(handle);
	}

	@Override
	public ClientBoundPacketData clone() {
		ClientBoundPacketData clone = pool.get().init(packetType);
		getBytes(readerIndex(), clone);
		return clone;
	}

}
