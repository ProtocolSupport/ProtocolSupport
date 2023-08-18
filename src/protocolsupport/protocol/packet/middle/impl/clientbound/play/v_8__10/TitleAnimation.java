package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__10;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleTitleAnimation;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;

public class TitleAnimation extends MiddleTitleAnimation implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10 {

	public TitleAnimation(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData titlesubtextPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TITLE_ANIMATION);
		VarNumberCodec.writeVarInt(titlesubtextPacket, 2); //legacy title action (2 - set animation)
		titlesubtextPacket.writeInt(fadeIn);
		titlesubtextPacket.writeInt(stay);
		titlesubtextPacket.writeInt(fadeOut);
		io.writeClientbound(titlesubtextPacket);
	}

}
