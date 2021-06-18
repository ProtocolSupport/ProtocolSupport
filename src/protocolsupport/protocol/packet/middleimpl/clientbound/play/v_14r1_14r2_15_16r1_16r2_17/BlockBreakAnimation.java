package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockBreakAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class BlockBreakAnimation extends MiddleBlockBreakAnimation {

	public BlockBreakAnimation(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData blockbreakanimation = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_BREAK_ANIMATION);
		VarNumberCodec.writeVarInt(blockbreakanimation, entityId);
		PositionCodec.writePosition(blockbreakanimation, position);
		blockbreakanimation.writeByte(stage);
		codec.writeClientbound(blockbreakanimation);
	}

}
