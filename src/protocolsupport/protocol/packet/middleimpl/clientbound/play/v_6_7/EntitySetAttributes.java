package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7;

import java.util.ArrayList;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;

public class EntitySetAttributes extends MiddleEntitySetAttributes {

	public EntitySetAttributes(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData entitysetattributes = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_ATTRIBUTES);
		GenericSkippingTable<String> table = GenericIdSkipper.ATTRIBUTES.getTable(version);
		ArrayList<Attribute> sendattrs = new ArrayList<>();
		for (Attribute attribute : attributes.values()) {
			if (!table.shouldSkip(attribute.key)) {
				sendattrs.add(attribute);
			}
		}
		entitysetattributes.writeInt(entityId);
		entitysetattributes.writeInt(sendattrs.size());
		for (Attribute attribute : sendattrs) {
			StringSerializer.writeString(entitysetattributes, version, attribute.key);
			entitysetattributes.writeDouble(attribute.value);
			if (version != ProtocolVersion.MINECRAFT_1_6_1) {
				entitysetattributes.writeShort(attribute.modifiers.length);
				for (Modifier modifier : attribute.modifiers) {
					MiscSerializer.writeUUID(entitysetattributes, modifier.uuid);
					entitysetattributes.writeDouble(modifier.amount);
					entitysetattributes.writeByte(modifier.operation);
				}
			}
		}
		codec.write(entitysetattributes);
	}

}
