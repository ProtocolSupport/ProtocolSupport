package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5_1_6_1_7;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityRelMove;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupportbuildprocessor.annotations.NeedsNoArgConstructor;

@NeedsNoArgConstructor
public class EntityRelMove extends MiddleEntityRelMove<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(entityId);
		serializer.writeByte(relX);
		serializer.writeByte(relY);
		serializer.writeByte(relZ);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_ENTITY_REL_MOVE_ID, serializer));
	}

}
