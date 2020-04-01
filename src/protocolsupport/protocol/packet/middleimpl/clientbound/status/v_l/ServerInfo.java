package protocolsupport.protocol.packet.middleimpl.clientbound.status.v_l;

import java.util.StringJoiner;

import org.bukkit.ChatColor;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class ServerInfo extends MiddleServerInfo {

	public ServerInfo(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData serverinfo = ClientBoundPacketData.create(PacketType.CLIENTBOUND_STATUS_SERVER_INFO);
		String response = new StringJoiner("§")
		.add(ChatColor.stripColor(ping.getMotd().toLegacyText(cache.getAttributesCache().getLocale())).replace("§", ""))
		.add(String.valueOf(ping.getPlayers().getOnline()))
		.add(String.valueOf(ping.getPlayers().getMax()))
		.toString();
		StringSerializer.writeShortUTF16BEString(serverinfo, response);
		codec.write(serverinfo);
	}

}
