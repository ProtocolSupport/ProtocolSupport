package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTabComplete;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class TabComplete extends MiddleTabComplete {

	public TabComplete(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData tabcomplete = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_TAB_COMPLETE);
		VarNumberSerializer.writeVarInt(tabcomplete, id);
		VarNumberSerializer.writeVarInt(tabcomplete, start);
		VarNumberSerializer.writeVarInt(tabcomplete, length);
		ArraySerializer.writeVarIntTArray(tabcomplete, matches, (to, match) -> {
			StringSerializer.writeVarIntUTF8String(to, match.getMatch());
			to.writeBoolean(match.hasTooltip());
			if (match.hasTooltip()) {
				StringSerializer.writeVarIntUTF8String(to, match.getTooltip());
			}
		});
		codec.writeClientbound(tabcomplete);
	}

}
