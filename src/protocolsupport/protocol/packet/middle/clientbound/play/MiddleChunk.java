package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleChunk<T> extends ClientBoundMiddlePacket<T> {

	protected int chunkX;
	protected int chunkZ;
	protected boolean full;
	protected int bitmask;
	protected byte[] data;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		chunkX = serializer.readInt();
		chunkZ = serializer.readInt();
		full = serializer.readBoolean();
		bitmask = serializer.readVarInt();
		data = serializer.readArray();
	}

}
