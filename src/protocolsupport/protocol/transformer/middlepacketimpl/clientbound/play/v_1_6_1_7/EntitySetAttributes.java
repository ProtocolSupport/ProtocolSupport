package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_6_1_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleEntitySetAttributes;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntitySetAttributes extends MiddleEntitySetAttributes<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, version);
		serializer.writeInt(entityId);
		serializer.writeInt(attributes.length);
		for (Attribute attribute : attributes) {
			serializer.writeString(attribute.key);
			serializer.writeDouble(attribute.value);
			if (version != ProtocolVersion.MINECRAFT_1_6_1) {
				serializer.writeShort(attribute.modifiers.length);
				for (Modifier modifier : attribute.modifiers) {
					serializer.writeUUID(modifier.uuid);
					serializer.writeDouble(modifier.amount);
					serializer.writeByte(modifier.operation);
				}
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
