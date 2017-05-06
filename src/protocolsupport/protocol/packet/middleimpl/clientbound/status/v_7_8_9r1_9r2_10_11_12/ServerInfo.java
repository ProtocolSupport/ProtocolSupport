package protocolsupport.protocol.packet.middleimpl.clientbound.status.v_7_8_9r1_9r2_10_11_12;

import protocolsupport.api.ProtocolType;
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
		if (ping.getProtocolData().getVersion() == ProtocolVersion.getLatest(ProtocolType.PC).getId()) {
			ping.getProtocolData().setVersion(version.getId());
		}
		StringSerializer.writeString(serializer, version, PingResponse.toJson(ping));
		return RecyclableSingletonList.create(serializer);
	}

}
