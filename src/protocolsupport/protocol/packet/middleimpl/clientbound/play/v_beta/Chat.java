package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import org.bukkit.ChatColor;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.Utils;

public class Chat extends MiddleChat {

	public Chat(ConnectionImpl connection) {
		super(connection);
	}

	protected static final int maxlength = 119;

	@Override
	public void writeToClient() {
		String legacyMessage = message.toLegacyText(cache.getAttributesCache().getLocale());
		int legacyMessageLength = legacyMessage.length();

		int splits = Utils.getSplitCount(legacyMessageLength, maxlength);
		String lastColors = null;
		for (int i = 0; i < splits; i++) {
			int startIndex = maxlength * i;
			int endIndex = startIndex + maxlength;
			if (endIndex > legacyMessageLength) {
				endIndex = legacyMessageLength;
			}
			String substring = legacyMessage.substring(startIndex, endIndex);
			if (lastColors != null) {
				substring = lastColors + substring;
			}
			lastColors = ChatColor.getLastColors(substring);
			ClientBoundPacketData chat = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHAT);
			StringSerializer.writeShortUTF16BEString(chat, substring);
			codec.write(chat);
		}
	}

}
