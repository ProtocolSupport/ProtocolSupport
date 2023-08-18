package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_9r1__10;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV9r2;
import protocolsupport.protocol.types.UsedHand;

public class BlockPlace extends MiddleBlockPlace implements
IServerboundMiddlePacketV9r1,
IServerboundMiddlePacketV9r2,
IServerboundMiddlePacketV10 {

	public BlockPlace(IMiddlePacketInit init) {
		super(init);
		insideblock = false;
	}

	@Override
	protected void read(ByteBuf clientdata) {
		PositionCodec.readPositionLXYZ(clientdata, position);
		face = VarNumberCodec.readVarInt(clientdata);
		hand = MiscDataCodec.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
		cX = clientdata.readUnsignedByte() / 16.0F;
		cY = clientdata.readUnsignedByte() / 16.0F;
		cZ = clientdata.readUnsignedByte() / 16.0F;
	}

}
