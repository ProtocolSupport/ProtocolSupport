package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.types.UsedHand;

public abstract class MiddleAnimation extends ServerBoundMiddlePacket {

	protected MiddleAnimation(IMiddlePacketInit init) {
		super(init);
	}

	protected UsedHand hand;

	@Override
	protected void write() {
		io.writeServerbound(create(hand));
	}

	public static ServerBoundPacketData create(UsedHand hand) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_ANIMATION);
		MiscDataCodec.writeVarIntEnum(creator, hand);
		return creator;
	}

}
