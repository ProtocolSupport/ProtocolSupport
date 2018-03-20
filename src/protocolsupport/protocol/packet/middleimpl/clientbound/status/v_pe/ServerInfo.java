package protocolsupport.protocol.packet.middleimpl.clientbound.status.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.utils.pingresponse.PingResponse;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.impl.pe.PEProxyServerInfoHandler;

public class ServerInfo extends MiddleServerInfo {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEProxyServerInfoHandler.PACKET_ID, version);
		StringSerializer.writeString(serializer, version, PingResponse.toJson(ping));
		return RecyclableSingletonList.create(serializer);
	}

}
