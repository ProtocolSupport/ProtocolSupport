package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.codec.VarNumberCodec;
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
		VarNumberCodec.writeVarInt(titlesubtextPacket, 3); //legacy title action (3 - set animation)
		titlesubtextPacket.writeInt(fadeIn);
		titlesubtextPacket.writeInt(stay);
		titlesubtextPacket.writeInt(fadeOut);
		codec.writeClientbound(titlesubtextPacket);
	}

}
