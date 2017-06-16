package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;

public abstract class MiddleEntityDestroy extends ClientBoundMiddlePacket {

	protected int[] entityIds;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityIds = ArraySerializer.readVarIntVarIntArray(serverdata);
	}

	@Override
	public void handle() {
		cache.removeWatchedEntities(entityIds);
	}

}
