package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleUnloadChunk<T> extends ClientBoundMiddlePacket<T> {

	protected int chunkX;
	protected int chunkZ;

	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		chunkX = serializer.readInt();
		chunkZ = serializer.readInt();
	}

}
