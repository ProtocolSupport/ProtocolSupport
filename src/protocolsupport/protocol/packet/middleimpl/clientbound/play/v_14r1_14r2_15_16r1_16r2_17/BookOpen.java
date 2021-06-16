package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBookOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;

public class BookOpen extends MiddleBookOpen {

	public BookOpen(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData bookopen = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_BOOK_OPEN);
		MiscSerializer.writeVarIntEnum(bookopen, hand);
		codec.writeClientbound(bookopen);
	}

}
