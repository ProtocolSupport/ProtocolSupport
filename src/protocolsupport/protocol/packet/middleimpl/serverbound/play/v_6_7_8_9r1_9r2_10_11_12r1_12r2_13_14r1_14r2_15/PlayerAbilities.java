package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePlayerAbilities;

public class PlayerAbilities extends MiddlePlayerAbilities {

	public PlayerAbilities(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		flags = clientdata.readUnsignedByte();
		clientdata.skipBytes(Float.BYTES * 2); //fly+walk speeds
	}

}
