package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleSteerVehicle;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV7;
import protocolsupport.utils.BitUtils;

public class SteerVehicle extends MiddleSteerVehicle implements
IServerboundMiddlePacketV6,
IServerboundMiddlePacketV7 {

	public SteerVehicle(IMiddlePacketInit init) {
		super(init);
	}

	protected static final int[] flags_mask = {FLAGS_BIT_JUMPING, FLAGS_BIT_UNMOUNT};

	@Override
	protected void read(ByteBuf clientdata) {
		sideForce = clientdata.readFloat();
		forwardForce = clientdata.readFloat();
		flags = BitUtils.createIBitMaskFromBits(flags_mask, new int[] {clientdata.readByte(), clientdata.readByte()});
	}

}
