package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.types.UsedHand;

public abstract class MiddleAnimation extends ServerBoundMiddlePacket {

	protected MiddleAnimation(MiddlePacketInit init) {
		super(init);
	}

	protected UsedHand hand;

	@Override
	protected void write() {
		codec.writeServerbound(create(hand));
	}

	public static ServerBoundPacketData create(UsedHand hand) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_ANIMATION);
		MiscDataCodec.writeVarIntEnum(creator, hand);
		return creator;
	}

}
