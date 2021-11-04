package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6_7;

import io.netty.buffer.Unpooled;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBookOpen;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV7;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class BookOpen extends MiddleBookOpen implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6,
IClientboundMiddlePacketV7
{

	public BookOpen(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(CustomPayload.create(version, LegacyCustomPayloadChannelName.LEGACY_BOOK_OPEN, Unpooled.EMPTY_BUFFER));
	}

}
