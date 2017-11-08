package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import java.util.ArrayList;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdSkipper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntitySetAttributes extends MiddleEntitySetAttributes {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, version);
		GenericSkippingTable<String> table = IdSkipper.ATTRIBUTES.getTable(version);
		ArrayList<Attribute> sendattrs = new ArrayList<>();
		for (Attribute attribute : attributes.values()) {
			if (!table.shouldSkip(attribute.key)) {
				sendattrs.add(attribute);
			}
		}
		VarNumberSerializer.writeVarInt(serializer, entityId);
		serializer.writeInt(sendattrs.size());
		for (Attribute attribute : sendattrs) {
			StringSerializer.writeString(serializer, version, attribute.key);
			serializer.writeDouble(attribute.value);
			VarNumberSerializer.writeVarInt(serializer, attribute.modifiers.length);
			for (Modifier modifier : attribute.modifiers) {
				MiscSerializer.writeUUID(serializer, connection.getVersion(), modifier.uuid);
				serializer.writeDouble(modifier.amount);
				serializer.writeByte(modifier.operation);
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
