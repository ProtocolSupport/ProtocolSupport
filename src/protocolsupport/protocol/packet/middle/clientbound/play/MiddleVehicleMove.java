package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

//TODO: Find out how and when it is used
public abstract class MiddleVehicleMove extends ClientBoundMiddlePacket {

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
		yaw = serializer.readFloat();
		pitch = serializer.readFloat();
	}

}
