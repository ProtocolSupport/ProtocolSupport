package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.storage.netcache.WatchedEntityCache;

public abstract class MiddleEntityDestroy extends ClientBoundMiddlePacket {

	protected final WatchedEntityCache entityCache = cache.getWatchedEntityCache();

	public MiddleEntityDestroy(ConnectionImpl connection) {
		super(connection);
	}

	protected int[] entityIds;

	@Override
	public void readServerData(ByteBuf serverdata) {
		entityIds = ArraySerializer.readVarIntVarIntArray(serverdata);
	}

	@Override
	public void handleReadData() {
		entityCache.removeWatchedEntities(entityIds);
	}

}
