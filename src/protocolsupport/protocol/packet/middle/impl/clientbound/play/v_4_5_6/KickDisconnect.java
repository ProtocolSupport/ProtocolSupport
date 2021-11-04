package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleKickDisconnect;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV4;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;

public class KickDisconnect extends MiddleKickDisconnect implements
IClientboundMiddlePacketV4,
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6
{

	public KickDisconnect(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData kickdisconnect = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_KICK_DISCONNECT);
		StringCodec.writeShortUTF16BEString(kickdisconnect, LegacyChat.clampLegacyText(message.toLegacyText(cache.getClientCache().getLocale()), 256));
		io.writeClientbound(kickdisconnect);
	}

}
