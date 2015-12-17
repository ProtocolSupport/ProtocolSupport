package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityAttach;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.middlepacketimpl.SupportedVersions;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
@SupportedVersions({ProtocolVersion.MINECRAFT_1_5_2, ProtocolVersion.MINECRAFT_1_4_7})
public class EntityAttach extends MiddleEntityAttach<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		if (leash) {
			return Collections.emptyList();
		} else {
			PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
			serializer.writeInt(entityId);
			serializer.writeInt(vehicleId);
			return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_ENTITY_ATTACH_ID, serializer));
		}
	}

}
