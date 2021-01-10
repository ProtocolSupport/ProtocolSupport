package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import io.netty.buffer.Unpooled;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBookOpen;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class BookOpen extends MiddleBookOpen {

	public BookOpen(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		codec.writeClientbound(CustomPayload.create(version, LegacyCustomPayloadChannelName.LEGACY_BOOK_OPEN, Unpooled.EMPTY_BUFFER));
	}

}
