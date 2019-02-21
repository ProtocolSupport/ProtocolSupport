package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7;

import java.util.ArrayList;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntitySetAttributes extends MiddleEntitySetAttributes {

	public EntitySetAttributes(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID);
		GenericSkippingTable<String> table = GenericIdSkipper.ATTRIBUTES.getTable(version);
		ArrayList<Attribute> sendattrs = new ArrayList<>();
		for (Attribute attribute : attributes.values()) {
			if (!table.shouldSkip(attribute.key)) {
				sendattrs.add(attribute);
			}
		}
		serializer.writeInt(entityId);
		serializer.writeInt(sendattrs.size());
		for (Attribute attribute : sendattrs) {
			StringSerializer.writeString(serializer, version, attribute.key);
			serializer.writeDouble(attribute.value);
			if (version != ProtocolVersion.MINECRAFT_1_6_1) {
				serializer.writeShort(attribute.modifiers.length);
				for (Modifier modifier : attribute.modifiers) {
					MiscSerializer.writeUUID(serializer, modifier.uuid);
					serializer.writeDouble(modifier.amount);
					serializer.writeByte(modifier.operation);
				}
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
