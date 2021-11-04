package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8_9r1_9r2_10;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleTitleClear;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;

public class TitleClear extends MiddleTitleClear implements
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10 {

	public TitleClear(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData titleclearPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TITLE_CLEAR);
		VarNumberCodec.writeVarInt(titleclearPacket, reset ? 4 : 3); //legacy title action (3 - hide title, 4 - reset title)
		io.writeClientbound(titleclearPacket);
	}

}
