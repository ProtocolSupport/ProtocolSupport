package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.status.v_1_5_1_6;

import java.io.IOException;

import net.minecraft.server.v1_8_R3.ServerPing;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.protocol.transformer.utils.ServerPingSerializers;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ServerInfo extends MiddleServerInfo<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.STATUS_SERVER_INFO_ID, version);
		ServerPing serverPing = ServerPingSerializers.PING_GSON.fromJson(pingJson, ServerPing.class);
		int versionId = serverPing.c().b();
		String response =
			"§1\u0000" +
			(versionId == ProtocolVersion.getLatest().getId() ? serializer.getVersion().getId() : versionId) +
			"\u0000" +
			serverPing.c().a() +
			"\u0000" +
			LegacyUtils.toText(serverPing.a()) +
			"\u0000" +
			serverPing.b().b() +
			"\u0000" +
			serverPing.b().a();
		serializer.writeString(response);
		return RecyclableSingletonList.create(serializer);
	}

}
