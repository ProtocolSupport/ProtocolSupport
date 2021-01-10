package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSteerVehicle;
import protocolsupport.utils.BitUtils;

public class SteerVehicle extends MiddleSteerVehicle {

	public SteerVehicle(MiddlePacketInit init) {
		super(init);
	}

	protected static final int[] flags_mask = new int[] {FLAGS_BIT_JUMPING, FLAGS_BIT_UNMOUNT};

	@Override
	protected void read(ByteBuf clientdata) {
		sideForce = clientdata.readFloat();
		forwardForce = clientdata.readFloat();
		flags = BitUtils.createIBitMaskFromBits(flags_mask, new int[] {clientdata.readByte(), clientdata.readByte()});
	}

}
