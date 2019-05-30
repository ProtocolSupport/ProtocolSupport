package protocolsupport.protocol.packet.middleimpl.clientbound.status.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.pingresponse.PingResponse;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.impl.pe.PEProxyServerInfoHandler;

public class ServerInfo extends MiddleServerInfo {

	public ServerInfo(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEProxyServerInfoHandler.PACKET_ID);
		StringSerializer.writeString(serializer, ProtocolVersionsHelper.LATEST_PC, PingResponse.toJson(ping));
		return RecyclableSingletonList.create(serializer);
	}

}
