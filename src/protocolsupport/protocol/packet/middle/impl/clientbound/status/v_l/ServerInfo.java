package protocolsupport.protocol.packet.middle.impl.clientbound.status.v_l;

import java.util.StringJoiner;

import protocolsupport.api.chat.ChatFormat;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.status.MiddleServerInfo;

public class ServerInfo extends MiddleServerInfo {

	public ServerInfo(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData serverinfo = ClientBoundPacketData.create(ClientBoundPacketType.STATUS_SERVER_INFO);
		String response = new StringJoiner("§")
		.add(ChatFormat.stripFormat(ping.getMotd().toLegacyText(cache.getClientCache().getLocale())).replace("§", ""))
		.add(String.valueOf(ping.getPlayers().getOnline()))
		.add(String.valueOf(ping.getPlayers().getMax()))
		.toString();
		StringCodec.writeShortUTF16BEString(serverinfo, response);
		io.writeClientbound(serverinfo);
	}

}
