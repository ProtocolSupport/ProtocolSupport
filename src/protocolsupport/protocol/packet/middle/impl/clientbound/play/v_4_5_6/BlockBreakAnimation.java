package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockBreakAnimation;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;

public class BlockBreakAnimation extends MiddleBlockBreakAnimation implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6
{

	public BlockBreakAnimation(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData blockbreakanimation = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BLOCK_BREAK_ANIMATION);
		blockbreakanimation.writeInt(entityId);
		PositionCodec.writePositionIII(blockbreakanimation, position);
		blockbreakanimation.writeByte(stage);
		io.writeClientbound(blockbreakanimation);
	}

}
