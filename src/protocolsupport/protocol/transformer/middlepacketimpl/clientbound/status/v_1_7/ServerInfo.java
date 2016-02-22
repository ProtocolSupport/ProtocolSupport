package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.status.v_1_7;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.ServerPing;
import net.minecraft.server.v1_8_R3.ServerPing.ServerData;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.utils.ServerPingSerializers;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ServerInfo extends MiddleServerInfo<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.STATUS_SERVER_INFO_ID, version);
		ServerPing serverPing = ServerPingSerializers.PING_GSON.fromJson(pingJson, ServerPing.class);
		int versionId = serverPing.c().b();
		serverPing.setServerInfo(new ServerData(serverPing.c().a(), versionId == ProtocolVersion.getLatest().getId() ? serializer.getVersion().getId() : versionId));
		serializer.writeString(ServerPingSerializers.PING_GSON.toJson(serverPing));
		return RecyclableSingletonList.create(serializer);
	}

}
