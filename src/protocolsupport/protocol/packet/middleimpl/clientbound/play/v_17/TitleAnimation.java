package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_17;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class TitleAnimation extends MiddleTitleAnimation {

	public TitleAnimation(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData titlesubtextPacket = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_TITLE_ANIMATION);
		titlesubtextPacket.writeInt(fadeIn);
		titlesubtextPacket.writeInt(stay);
		titlesubtextPacket.writeInt(fadeOut);
		codec.writeClientbound(titlesubtextPacket);
	}

}
