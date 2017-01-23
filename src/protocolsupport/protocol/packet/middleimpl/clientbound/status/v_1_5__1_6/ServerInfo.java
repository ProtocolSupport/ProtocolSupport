package protocolsupport.protocol.packet.middleimpl.clientbound.status.v_1_5__1_6;

import java.util.StringJoiner;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.pingresponse.PingResponse;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ServerInfo extends MiddleServerInfo<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.STATUS_SERVER_INFO_ID, version);
		PingResponse ping = PingResponse.fromJson(pingJson);
		int versionId = ping.getProtocolData().getVersion();
		String response = new StringJoiner("\u0000")
		.add("§1")
		.add(String.valueOf(versionId == ProtocolVersion.getLatest().getId() ? serializer.getVersion().getId() : versionId))
		.add(ping.getProtocolData().getName())
		.add(ping.getMotd().toLegacyText())
		.add(String.valueOf(ping.getPlayers().getOnline()))
		.add(String.valueOf(ping.getPlayers().getMax()))
		.toString();
		serializer.writeString(response);
		return RecyclableSingletonList.create(serializer);
	}

}
