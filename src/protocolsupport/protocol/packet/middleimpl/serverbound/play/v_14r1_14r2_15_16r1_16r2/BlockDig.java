package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;

public class BlockDig extends MiddleBlockDig {

	public BlockDig(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		status = MiscDataCodec.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		PositionCodec.readPosition(clientdata, position);
		face = clientdata.readUnsignedByte();
	}

}
