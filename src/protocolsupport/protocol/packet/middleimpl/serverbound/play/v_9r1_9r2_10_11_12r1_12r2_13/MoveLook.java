package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleMoveLook;

public class MoveLook extends MiddleMoveLook {

	public MoveLook(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		x = clientdata.readDouble();
		y = clientdata.readDouble();
		z = clientdata.readDouble();
		yaw = clientdata.readFloat();
		pitch = clientdata.readFloat();
		onGround = clientdata.readBoolean();
	}

}
