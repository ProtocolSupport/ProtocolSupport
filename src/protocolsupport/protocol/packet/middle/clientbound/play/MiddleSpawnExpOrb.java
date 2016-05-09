package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

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
