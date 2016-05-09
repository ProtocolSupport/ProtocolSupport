package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleUnloadChunk<T> extends ClientBoundMiddlePacket<T> {

	protected int chunkX;
	protected int chunkZ;

	public void readFromServerData(PacketDataSerializer serializer) {
		chunkX = serializer.readInt();
		chunkZ = serializer.readInt();
	}

}
