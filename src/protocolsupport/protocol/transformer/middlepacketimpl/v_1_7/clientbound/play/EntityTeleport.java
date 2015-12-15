package protocolsupport.protocol.transformer.middlepacketimpl.v_1_7.clientbound.play;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificType;

public class EntityTeleport extends MiddleEntityTeleport<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		if ((wentity != null) && (wentity.getType() == SpecificType.TNT || wentity.getType() == SpecificType.FALLING_OBJECT)) {
			y += 16;
		}
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(entityId);
		serializer.writeInt(x);
		serializer.writeInt(y);
		serializer.writeInt(z);
		serializer.writeByte(yaw);
		serializer.writeByte(pitch);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_ENTITY_TELEPORT_ID, serializer));
	}

}
