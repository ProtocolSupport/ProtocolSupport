package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;

public abstract class MiddleEntityDestroy extends ClientBoundMiddlePacket {

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	public MiddleEntityDestroy(MiddlePacketInit init) {
		super(init);
	}

	protected int[] entityIds;

	@Override
	protected void decode(ByteBuf serverdata) {
		entityIds = ArraySerializer.readVarIntVarIntArray(serverdata);
	}

	@Override
	protected void handle() {
		entityCache.removeEntities(entityIds);
	}

}
