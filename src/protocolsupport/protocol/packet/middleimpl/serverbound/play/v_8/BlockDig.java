package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8;

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
		status = MiscDataCodec.readByteEnum(clientdata, Action.CONSTANT_LOOKUP);
		PositionCodec.readPositionLXYZ(clientdata, position);
		face = clientdata.readUnsignedByte();
	}

}
