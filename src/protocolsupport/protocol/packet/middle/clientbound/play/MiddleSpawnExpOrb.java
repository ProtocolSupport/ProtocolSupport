package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleSpawnExpOrb extends ClientBoundMiddlePacket {

	protected int entityId;
	protected double x;
	protected double y;
	protected double z;
	protected int count;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		entityId = serializer.readVarInt();
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
		count = serializer.readShort();
	}

}
