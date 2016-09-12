package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntityDestroy<T> extends ClientBoundMiddlePacket<T> {

	protected int[] entityIds;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
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
