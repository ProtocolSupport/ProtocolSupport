package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKickDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;

public class KickDisconnect extends MiddleKickDisconnect {

	public KickDisconnect(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData kickdisconnect = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_KICK_DISCONNECT);
		StringSerializer.writeString(kickdisconnect, version, LegacyChat.clampLegacyText(message.toLegacyText(cache.getAttributesCache().getLocale()), 100));
		codec.write(kickdisconnect);
	}

}
