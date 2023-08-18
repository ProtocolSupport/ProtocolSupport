package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV20;
import protocolsupport.protocol.types.UsedHand;

public class BlockPlace extends MiddleBlockPlace implements
IServerboundMiddlePacketV20 {

	public BlockPlace(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		hand = MiscDataCodec.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
		PositionCodec.readPosition(clientdata, position);
		face = VarNumberCodec.readVarInt(clientdata);
		cX = clientdata.readFloat();
		cY = clientdata.readFloat();
		cZ = clientdata.readFloat();
		insideblock = clientdata.readBoolean();
		sequence = VarNumberCodec.readVarInt(clientdata);
	}

}
