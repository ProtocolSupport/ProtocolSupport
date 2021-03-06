package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleAdvancementsTab;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class AdvancementsTab extends MiddleAdvancementsTab {

	public AdvancementsTab(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData advanvementstab = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_ADVANCEMENTS_TAB);
		if (identifier != null) {
			advanvementstab.writeBoolean(true);
			StringCodec.writeVarIntUTF8String(advanvementstab, identifier);
		} else {
			advanvementstab.writeBoolean(false);
		}
		codec.writeClientbound(advanvementstab);
	}

}
