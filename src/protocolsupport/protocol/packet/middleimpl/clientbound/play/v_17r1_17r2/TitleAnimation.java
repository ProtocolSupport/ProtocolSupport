package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class TitleAnimation extends MiddleTitleAnimation {

	public TitleAnimation(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData titlesubtextPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TITLE_ANIMATION);
		titlesubtextPacket.writeInt(fadeIn);
		titlesubtextPacket.writeInt(stay);
		titlesubtextPacket.writeInt(fadeOut);
		codec.writeClientbound(titlesubtextPacket);
	}

}
