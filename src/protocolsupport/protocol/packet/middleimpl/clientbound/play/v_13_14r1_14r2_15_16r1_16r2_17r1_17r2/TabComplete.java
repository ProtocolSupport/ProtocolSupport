package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class TabComplete extends MiddleTabComplete {

	public TabComplete(MiddlePacketInit init) {
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
		codec.writeClientbound(tabcomplete);
	}

}
