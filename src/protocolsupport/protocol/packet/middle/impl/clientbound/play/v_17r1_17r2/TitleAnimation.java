package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1_17r2;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleTitleAnimation;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;

public class TitleAnimation extends MiddleTitleAnimation implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public TitleAnimation(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData titlesubtextPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TITLE_ANIMATION);
		titlesubtextPacket.writeInt(fadeIn);
		titlesubtextPacket.writeInt(stay);
		titlesubtextPacket.writeInt(fadeOut);
		io.writeClientbound(titlesubtextPacket);
	}

}
