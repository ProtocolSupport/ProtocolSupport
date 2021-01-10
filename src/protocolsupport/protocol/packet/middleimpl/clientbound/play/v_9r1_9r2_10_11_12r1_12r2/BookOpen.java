package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBookOpen;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.CustomPayload;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class BookOpen extends MiddleBookOpen {

	public BookOpen(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		codec.writeClientbound(CustomPayload.create(LegacyCustomPayloadChannelName.LEGACY_BOOK_OPEN, to -> MiscSerializer.writeByteEnum(to, hand)));
	}

}
