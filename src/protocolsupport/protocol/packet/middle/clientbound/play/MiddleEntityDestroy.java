package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;

public abstract class MiddleEntityDestroy extends ClientBoundMiddlePacket {

	public MiddleEntityDestroy(ConnectionImpl connection) {
		super(connection);
	}

	protected int[] entityIds;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityIds = ArraySerializer.readVarIntVarIntArray(serverdata);
	}

	@Override
	public boolean postFromServerRead() {
		cache.getWatchedEntityCache().removeWatchedEntities(entityIds);
		return true;
	}

}
