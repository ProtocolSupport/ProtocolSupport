package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_20;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV20;
import protocolsupport.protocol.types.UsedHand;

public class UseItem extends MiddleBlockPlace implements
IServerboundMiddlePacketV20 {

	public UseItem(IMiddlePacketInit init) {
		super(init);
		face = -1;
	}

	@Override
	protected void read(ByteBuf clientdata) {
		hand = MiscDataCodec.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
		sequence = VarNumberCodec.readVarInt(clientdata);
	}

}
