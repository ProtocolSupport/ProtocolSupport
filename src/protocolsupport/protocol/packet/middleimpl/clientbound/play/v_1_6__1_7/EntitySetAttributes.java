package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_6__1_7;

import java.util.ArrayList;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupport.protocol.typeskipper.id.SkippingTable.GenericSkippingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntitySetAttributes extends MiddleEntitySetAttributes<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, version);
		GenericSkippingTable<String> table = IdSkipper.ATTRIBUTES.getTable(version);
		ArrayList<Attribute> sendattrs = new ArrayList<>();
		for (Attribute attribute : attributes) {
			if (!table.shouldSkip(attribute.key)) {
				sendattrs.add(attribute);
			}
		}
		serializer.writeInt(entityId);
		serializer.writeInt(sendattrs.size());
		for (Attribute attribute : sendattrs) {
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
