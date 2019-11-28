package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7;

import io.netty.buffer.Unpooled;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBookOpen;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class BookOpen extends MiddleBookOpen {

	public BookOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		codec.write(CustomPayload.create(version, LegacyCustomPayloadChannelName.LEGACY_BOOK_OPEN, Unpooled.EMPTY_BUFFER));
	}

}
