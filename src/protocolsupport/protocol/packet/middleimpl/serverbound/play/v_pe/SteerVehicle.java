package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerVehicle;

public class SteerVehicle extends MiddleSteerVehicle {
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		sideForce = clientdata.readFloatLE();
		forwardForce = clientdata.readFloatLE();
		clientdata.readBoolean(); //?
		clientdata.readBoolean(); //?
	}

}
