package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV20;

public class BlockDig extends MiddleBlockDig implements
IServerboundMiddlePacketV20 {

	public BlockDig(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		status = MiscDataCodec.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		PositionCodec.readPosition(clientdata, position);
		face = clientdata.readUnsignedByte();
		sequence = VarNumberCodec.readVarInt(clientdata);
	}

}
