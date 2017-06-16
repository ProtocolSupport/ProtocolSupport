package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerVehicle;

public class SteerVehicle extends MiddleSteerVehicle {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		sideForce = clientdata.readFloat();
		forwardForce = clientdata.readFloat();
		flags = (clientdata.readBoolean() ? 1 : 0) + (clientdata.readBoolean() ? 1 << 1 : 0);
	}

}
