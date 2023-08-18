package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_9r1__12r2;

import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBookOpen;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV10;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV11;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV12r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV9r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__12r2.CustomPayload;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class BookOpen extends MiddleBookOpen implements
IClientboundMiddlePacketV9r1,
IClientboundMiddlePacketV9r2,
IClientboundMiddlePacketV10,
IClientboundMiddlePacketV11,
IClientboundMiddlePacketV12r1,
IClientboundMiddlePacketV12r2 {

	public BookOpen(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(CustomPayload.create(LegacyCustomPayloadChannelName.LEGACY_BOOK_OPEN, to -> MiscDataCodec.writeByteEnum(to, hand)));
	}

}
