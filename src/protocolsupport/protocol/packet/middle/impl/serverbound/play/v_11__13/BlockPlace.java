package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_11__13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV13;
import protocolsupport.protocol.types.UsedHand;

public class BlockPlace extends MiddleBlockPlace implements
IServerboundMiddlePacketV11,
IServerboundMiddlePacketV12r1,
IServerboundMiddlePacketV12r2,
IServerboundMiddlePacketV13 {

	public BlockPlace(IMiddlePacketInit init) {
		super(init);
		insideblock = false;
	}

	@Override
	protected void read(ByteBuf clientdata) {
		PositionCodec.readPositionLXYZ(clientdata, position);
		face = VarNumberCodec.readVarInt(clientdata);
		hand = MiscDataCodec.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
		cX = clientdata.readFloat();
		cY = clientdata.readFloat();
		cZ = clientdata.readFloat();
	}

}