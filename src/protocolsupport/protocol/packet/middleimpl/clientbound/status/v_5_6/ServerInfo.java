package protocolsupport.protocol.packet.middleimpl.clientbound.status.v_5_6;

import java.util.StringJoiner;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ServerInfo extends MiddleServerInfo {

	public ServerInfo(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.STATUS_SERVER_INFO_ID);
		String response = new StringJoiner("\u0000")
		.add("§1")
		.add(String.valueOf(ping.getProtocolData().getVersion()))
		.add(ping.getProtocolData().getName())
		.add(ping.getMotd().toLegacyText(cache.getAttributesCache().getLocale()))
		.add(String.valueOf(ping.getPlayers().getOnline()))
		.add(String.valueOf(ping.getPlayers().getMax()))
		.toString();
		StringSerializer.writeString(serializer, version, response);
		return RecyclableSingletonList.create(serializer);
	}

}
