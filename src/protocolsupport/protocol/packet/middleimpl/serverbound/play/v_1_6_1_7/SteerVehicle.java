package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_6_1_7;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerVehicle;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class SteerVehicle extends MiddleSteerVehicle {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) {
		sideForce = serializer.readFloat();
		forwardForce = serializer.readFloat();
		flags = (serializer.readBoolean() ? 1 : 0) + (serializer.readBoolean() ? 1 << 1 : 0);
	}

}
