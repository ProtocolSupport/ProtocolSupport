package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerVehicle;
import protocolsupport.utils.BitUtils;

public class SteerVehicle extends MiddleSteerVehicle {

	public SteerVehicle(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		sideForce = clientdata.readFloat();
		forwardForce = clientdata.readFloat();
		BitUtils.setBit(flags, FLAGS_BIT_JUMPING, clientdata.readBoolean());
		BitUtils.setBit(flags, FLAGS_BIT_UNMOUNT, clientdata.readBoolean());
	}

}
