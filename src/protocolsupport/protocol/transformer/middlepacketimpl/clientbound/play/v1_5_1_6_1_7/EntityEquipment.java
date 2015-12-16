package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v1_5_1_6_1_7;

import java.util.Collection;
import java.util.Collections;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntityEquipment;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;

public class EntityEquipment extends MiddleEntityEquipment<Collection<PacketData>> {

	@Override
	public Collection<PacketData> toData(ProtocolVersion version) {
		PacketDataSerializer serializer = PacketDataSerializer.createNew(version);
		serializer.writeInt(entityId);
		serializer.writeShort(slot);
		serializer.writeItemStack(itemstack);
		return Collections.singletonList(new PacketData(ClientBoundPacket.PLAY_ENTITY_EQUIPMENT_ID, serializer));
	}

}
