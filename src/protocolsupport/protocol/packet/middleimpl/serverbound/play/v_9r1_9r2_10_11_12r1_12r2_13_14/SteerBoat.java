package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerBoat;

public class SteerBoat extends MiddleSteerBoat {

	public SteerBoat(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		rightPaddleTurning = clientdata.readBoolean();
		leftPaddleTurning = clientdata.readBoolean();
	}

}
