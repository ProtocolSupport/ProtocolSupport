package protocolsupport.protocol.packet.middle.impl.clientbound.status.v_5_6;

import java.util.StringJoiner;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV5;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV6;

public class ServerInfo extends MiddleServerInfo implements
IClientboundMiddlePacketV5,
IClientboundMiddlePacketV6 {

	public ServerInfo(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData serverinfo = ClientBoundPacketData.create(ClientBoundPacketType.STATUS_SERVER_INFO);
		String response = new StringJoiner("\u0000")
		.add("§1")
		.add(String.valueOf(ping.getProtocolData().getVersion()))
		.add(ping.getProtocolData().getName())
		.add(ping.getMotd().toLegacyText(cache.getClientCache().getLocale()))
		.add(String.valueOf(ping.getPlayers().getOnline()))
		.add(String.valueOf(ping.getPlayers().getMax()))
		.toString();
		StringCodec.writeShortUTF16BEString(serverinfo, response);
		io.writeClientbound(serverinfo);
	}

}
