package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17r1_17r2_18;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;

public class TabComplete extends MiddleTabComplete implements
IClientboundMiddlePacketV13,
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public TabComplete(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData tabcomplete = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TAB_COMPLETE);
		VarNumberCodec.writeVarInt(tabcomplete, id);
		VarNumberCodec.writeVarInt(tabcomplete, start);
		VarNumberCodec.writeVarInt(tabcomplete, length);
		ArrayCodec.writeVarIntTArray(tabcomplete, matches, (to, match) -> {
			StringCodec.writeVarIntUTF8String(to, match.getMatch());
			to.writeBoolean(match.hasTooltip());
			if (match.hasTooltip()) {
				StringCodec.writeVarIntUTF8String(to, match.getTooltip());
			}
		});
		io.writeClientbound(tabcomplete);
	}

}
