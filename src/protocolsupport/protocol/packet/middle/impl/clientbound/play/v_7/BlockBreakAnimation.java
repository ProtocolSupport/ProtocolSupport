package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockBreakAnimation;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;

public class BlockBreakAnimation extends MiddleBlockBreakAnimation implements IClientboundMiddlePacketV7 {

	public BlockBreakAnimation(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData blockbreakanimation = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_BREAK_ANIMATION);
		VarNumberCodec.writeVarInt(blockbreakanimation, entityId);
		PositionCodec.writePositionIII(blockbreakanimation, position);
		blockbreakanimation.writeByte(stage);
		io.writeClientbound(blockbreakanimation);
	}

}
