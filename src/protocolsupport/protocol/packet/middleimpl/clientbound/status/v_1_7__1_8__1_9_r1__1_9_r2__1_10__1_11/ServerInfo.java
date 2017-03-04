package protocolsupport.protocol.packet.middleimpl.clientbound.status.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.pingresponse.PingResponse;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ServerInfo extends MiddleServerInfo {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.STATUS_SERVER_INFO_ID, version);
		PingResponse ping = PingResponse.fromJson(pingJson);
		if (ping.getProtocolData().getVersion() == ProtocolVersion.getLatest().getId()) {
			ping.getProtocolData().setVersion(version.getId());
		}
		StringSerializer.writeString(serializer, version, PingResponse.toJson(ping));
		return RecyclableSingletonList.create(serializer);
	}

}
