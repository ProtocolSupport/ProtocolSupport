package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockBreakAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;

public class BlockBreakAnimation extends MiddleBlockBreakAnimation {

	public BlockBreakAnimation(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData blockbreakanimation = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_BLOCK_BREAK_ANIMATION);
		blockbreakanimation.writeInt(entityId);
		PositionSerializer.writeLegacyPositionI(blockbreakanimation, position);
		blockbreakanimation.writeByte(stage);
		codec.writeClientbound(blockbreakanimation);
	}

}
