package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1__13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r2;

public class BlockDig extends MiddleBlockDig implements
IServerboundMiddlePacketV9r1,
IServerboundMiddlePacketV9r2,
IServerboundMiddlePacketV10,
IServerboundMiddlePacketV11,
IServerboundMiddlePacketV12r1,
IServerboundMiddlePacketV12r2,
IServerboundMiddlePacketV13 {

	public BlockDig(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		status = MiscDataCodec.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		PositionCodec.readPositionLXYZ(clientdata, position);
		face = clientdata.readUnsignedByte();
	}

}
