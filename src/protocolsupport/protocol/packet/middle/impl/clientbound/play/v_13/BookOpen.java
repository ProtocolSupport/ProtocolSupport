package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBookOpen;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13__20.CustomPayload;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class BookOpen extends MiddleBookOpen implements IClientboundMiddlePacketV13 {

	public BookOpen(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(CustomPayload.create(LegacyCustomPayloadChannelName.MODERN_BOOK_OPEN, to -> MiscDataCodec.writeVarIntEnum(to, hand)));
	}

}
