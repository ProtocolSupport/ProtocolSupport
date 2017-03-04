package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerVehicle;

public class SteerVehicle extends MiddleSteerVehicle {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		sideForce = clientdata.readFloat();
		forwardForce = clientdata.readFloat();
		flags = clientdata.readUnsignedByte();
	}

}
