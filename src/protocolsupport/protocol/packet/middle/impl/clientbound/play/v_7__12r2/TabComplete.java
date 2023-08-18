package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_7__12r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;

public class TabComplete extends MiddleTabComplete implements
IClientboundMiddlePacketV7,
IClientboundMiddlePacketV8,
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2 {

	public TabComplete(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		String prefix = start <= 1 ? "/" : "";
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TAB_COMPLETE);
		ArrayCodec.writeVarIntTArray(serializer, matches, (to, match) -> StringCodec.writeVarIntUTF8String(to, prefix + match.getMatch()));
		io.writeClientbound(serializer);
	}

}
