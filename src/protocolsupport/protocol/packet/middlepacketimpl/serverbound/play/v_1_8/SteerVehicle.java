package protocolsupport.protocol.packet.middlepacketimpl.serverbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.packet.middlepacket.serverbound.play.MiddleSteerVehicle;

public class SteerVehicle extends MiddleSteerVehicle  {

	@Override
	public void readFromClientData(PacketDataSerializer serializer) throws IOException {
		sideForce = serializer.readFloat();
		forwardForce = serializer.readFloat();
		flags = serializer.readUnsignedByte();
	}

}
