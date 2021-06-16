package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTitleAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class TitleAnimation extends MiddleTitleAnimation {

	public TitleAnimation(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData titlesubtextPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TITLE_ANIMATION);
		VarNumberSerializer.writeVarInt(titlesubtextPacket, 2); //legacy title action (2 - set animation)
		titlesubtextPacket.writeInt(fadeIn);
		titlesubtextPacket.writeInt(stay);
		titlesubtextPacket.writeInt(fadeOut);
		codec.writeClientbound(titlesubtextPacket);
	}

}
