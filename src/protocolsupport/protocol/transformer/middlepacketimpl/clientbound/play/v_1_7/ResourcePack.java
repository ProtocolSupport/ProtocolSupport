package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_7;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleResourcePack;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
@SupportedVersions({ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_7_5})
public class ResourcePack extends MiddleResourcePack<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeString("MC|RPack");
		serializer.writeArray(url.getBytes(StandardCharsets.UTF_8));
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_CUSTOM_PAYLOAD_ID, serializer));
	}

}
