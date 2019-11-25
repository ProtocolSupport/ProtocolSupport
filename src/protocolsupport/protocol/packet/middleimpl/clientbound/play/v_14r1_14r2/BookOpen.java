package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBookOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;

public class BookOpen extends MiddleBookOpen {

	public BookOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData bookopen = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_BOOK_OPEN);
		MiscSerializer.writeVarIntEnum(bookopen, hand);
		codec.write(bookopen);
	}

}
