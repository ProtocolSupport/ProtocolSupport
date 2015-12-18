package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.status.v_1_5_1_6;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import net.minecraft.server.v1_8_R3.ServerPing;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.protocol.transformer.utils.ServerPingSerializers;

public class ServerInfo extends MiddleServerInfo<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
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
		return Collections.singletonList(new PacketData(ClientBoundPacket.STATUS_SERVER_INFO_ID, serializer));
	}

}
