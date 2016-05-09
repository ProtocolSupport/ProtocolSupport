package protocolsupport.protocol.packet.middlepacket.clientbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.ClientBoundMiddlePacket;

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
