package protocolsupport.protocol.transformer.middlepacketimpl.v_1_6_1_7.serverbound.play;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.serverbound.play.MiddleSteerVehicle;

public class SteerVehicle extends MiddleSteerVehicle {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) {
		sideForce = serializer.readFloat();
		forwardForce = serializer.readFloat();
		flags = (serializer.readBoolean() ? 1 : 0) + (serializer.readBoolean() ? 1 << 1 : 0);
	}

}
