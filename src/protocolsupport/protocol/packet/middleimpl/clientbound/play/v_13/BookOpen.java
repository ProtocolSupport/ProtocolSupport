package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBookOpen;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14r1_14r2.CustomPayload;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class BookOpen extends MiddleBookOpen {

	public BookOpen(ConnectionImpl connection) {
		super(connection);
	}

	protected final ByteBuf buffer = Unpooled.buffer();

	@Override
	public void writeToClient() {
		MiscSerializer.writeVarIntEnum(buffer, hand);
		codec.write(CustomPayload.create(LegacyCustomPayloadChannelName.MODERN_BOOK_OPEN, buffer));
	}

	@Override
	public void postHandle() {
		buffer.clear();
	}

}
