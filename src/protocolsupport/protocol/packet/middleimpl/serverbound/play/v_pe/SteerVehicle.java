package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerVehicle;
import protocolsupport.protocol.serializer.MiscSerializer;

public class SteerVehicle extends MiddleSteerVehicle {
	
	@Override
	public void readFromClientData(ByteBuf clientdata) {
		sideForce = MiscSerializer.readLFloat(clientdata);
		forwardForce = MiscSerializer.readLFloat(clientdata);
		System.out.println("FORCE - S:" + sideForce + " - F:" + forwardForce);
		clientdata.readBoolean();
		clientdata.readBoolean();
	}

}
