package protocolsupport.protocol.packet.middleimpl.clientbound.status.v_l;

import java.util.StringJoiner;

import org.bukkit.ChatColor;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class ServerInfo extends MiddleServerInfo {

	public ServerInfo(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData serverinfo = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_STATUS_SERVER_INFO);
		String response = new StringJoiner("§")
		.add(ChatColor.stripColor(ping.getMotd().toLegacyText(cache.getClientCache().getLocale())).replace("§", ""))
		.add(String.valueOf(ping.getPlayers().getOnline()))
		.add(String.valueOf(ping.getPlayers().getMax()))
		.toString();
		StringSerializer.writeShortUTF16BEString(serverinfo, response);
		codec.writeClientbound(serverinfo);
	}

}
