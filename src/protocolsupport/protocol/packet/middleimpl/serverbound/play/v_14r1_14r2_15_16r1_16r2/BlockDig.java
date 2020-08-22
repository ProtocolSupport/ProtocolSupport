package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;

public class BlockDig extends MiddleBlockDig {

	public BlockDig(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		status = MiscSerializer.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		PositionSerializer.readPositionTo(clientdata, position);
		face = clientdata.readUnsignedByte();
	}

}
