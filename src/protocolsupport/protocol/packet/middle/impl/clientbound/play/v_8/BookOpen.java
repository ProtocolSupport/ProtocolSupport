package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import io.netty.buffer.Unpooled;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBookOpen;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8__12r2.CustomPayload;
import protocolsupport.protocol.typeremapper.legacy.LegacyCustomPayloadChannelName;

public class BookOpen extends MiddleBookOpen implements IClientboundMiddlePacketV8 {

	public BookOpen(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		io.writeClientbound(CustomPayload.create(LegacyCustomPayloadChannelName.LEGACY_BOOK_OPEN, Unpooled.EMPTY_BUFFER));
	}

}
