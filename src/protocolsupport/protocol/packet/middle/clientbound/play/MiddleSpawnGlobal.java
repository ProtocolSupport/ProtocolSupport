package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public abstract class MiddleSpawnGlobal<T> extends ClientBoundMiddlePacket<T> {

	protected int entityId;
	protected int type;
	protected double x;
	protected double y;
	protected double z;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		type = serializer.readUnsignedByte();
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
	}

}
