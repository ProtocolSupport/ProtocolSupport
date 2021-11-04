package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_8;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV8;

public class BlockDig extends MiddleBlockDig implements IServerboundMiddlePacketV8 {

	public BlockDig(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		status = MiscDataCodec.readByteEnum(clientdata, Action.CONSTANT_LOOKUP);
		PositionCodec.readPositionLXYZ(clientdata, position);
		face = clientdata.readUnsignedByte();
	}

}
