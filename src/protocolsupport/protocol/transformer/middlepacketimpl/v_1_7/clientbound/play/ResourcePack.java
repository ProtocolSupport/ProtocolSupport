package protocolsupport.protocol.transformer.middlepacketimpl.v_1_7.clientbound.play;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleResourcePack;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class ResourcePack extends MiddleResourcePack<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeString("MC|RPack");
		serializer.writeArray(url.getBytes(StandardCharsets.UTF_8));
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, serializer));
	}

}
