package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBookOpen;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2.CustomPayload;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class BookOpen extends MiddleBookOpen {

	public BookOpen(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		codec.writeClientbound(CustomPayload.create(LegacyCustomPayloadChannelName.MODERN_BOOK_OPEN, to -> MiscSerializer.writeVarIntEnum(to, hand)));
	}

}
