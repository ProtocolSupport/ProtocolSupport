package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_6_1_7;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityAttach;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class EntityAttach extends MiddleEntityAttach<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(entityId);
		serializer.writeInt(vehicleId);
		serializer.writeBoolean(leash);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_ENTITY_ATTACH_ID, serializer));
	}

}
