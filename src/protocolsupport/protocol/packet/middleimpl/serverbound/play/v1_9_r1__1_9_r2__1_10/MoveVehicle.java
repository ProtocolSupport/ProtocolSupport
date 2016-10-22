package protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleMoveVehicle;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class MoveVehicle extends MiddleMoveVehicle {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
		yaw = serializer.readFloat();
		pitch = serializer.readFloat();
	}

}
