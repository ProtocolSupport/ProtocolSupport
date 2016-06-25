package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerVehicle;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public class SteerVehicle extends MiddleSteerVehicle  {

	@Override
	public void readFromClientData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		sideForce = serializer.readFloat();
		forwardForce = serializer.readFloat();
		flags = serializer.readUnsignedByte();
	}

}
