package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleEntityDestroy<T> extends ClientBoundMiddlePacket<T> {

	protected int[] entityIds;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		entityIds = new int[serializer.readVarInt()];
		for (int i = 0; i < entityIds.length; i++) {
			entityIds[i] = serializer.readVarInt();
		}
	}

	@Override
	public void handle() {
		storage.removeWatchedEntities(entityIds);
	}

}
