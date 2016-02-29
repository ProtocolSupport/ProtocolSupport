package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleSpawnExpOrb<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected double x;
	protected double y;
	protected double z;
	protected int count;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
		count = serializer.readShort();
	}

}
