package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKickDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;

public class KickDisconnect extends MiddleKickDisconnect {

	public KickDisconnect(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData kickdisconnect = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_KICK_DISCONNECT);
		StringCodec.writeShortUTF16BEString(kickdisconnect, LegacyChat.clampLegacyText(message.toLegacyText(cache.getClientCache().getLocale()), 256));
		codec.writeClientbound(kickdisconnect);
	}

}
