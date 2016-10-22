package protocolsupport.protocol.packet.middleimpl.clientbound.status.v_1_7__1_8__1_9_r1__1_9_r2__1_10;

import net.minecraft.server.v1_10_R1.ServerPing;
import net.minecraft.server.v1_10_R1.ServerPing.ServerData;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.ServerPingSerializers;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ServerInfo extends MiddleServerInfo<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.STATUS_SERVER_INFO_ID, version);
		ServerPing serverPing = ServerPingSerializers.PING_GSON.fromJson(pingJson, ServerPing.class);
		int versionId = serverPing.getServerData().getProtocolVersion();
		serverPing.setServerInfo(new ServerData(serverPing.getServerData().a(), versionId == ProtocolVersion.getLatest().getId() ? serializer.getVersion().getId() : versionId));
		serializer.writeString(ServerPingSerializers.PING_GSON.toJson(serverPing));
		return RecyclableSingletonList.create(serializer);
	}

}
