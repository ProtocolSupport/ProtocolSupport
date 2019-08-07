package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerVehicle;
import protocolsupport.utils.BitUtils;

public class SteerVehicle extends MiddleSteerVehicle {

	public SteerVehicle(ConnectionImpl connection) {
		super(connection);
	}

	protected static final int[] flags_mask = new int[] {FLAGS_BIT_JUMPING, FLAGS_BIT_UNMOUNT};

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		sideForce = clientdata.readFloat();
		forwardForce = clientdata.readFloat();
		flags = BitUtils.createIBitMaskFromBits(flags_mask, new int[] {clientdata.readByte(), clientdata.readByte()});
	}

}
