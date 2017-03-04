package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleEntityDestroy extends ClientBoundMiddlePacket {

	protected int[] entityIds;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityIds = new int[VarNumberSerializer.readVarInt(serverdata)];
		for (int i = 0; i < entityIds.length; i++) {
			entityIds[i] = VarNumberSerializer.readVarInt(serverdata);
		}
	}

	@Override
	public void handle() {
		cache.removeWatchedEntities(entityIds);
	}

}
