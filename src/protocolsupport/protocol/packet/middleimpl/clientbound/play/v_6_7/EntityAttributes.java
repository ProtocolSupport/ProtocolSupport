package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7;

import java.util.ArrayList;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityAttributes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityAttribute;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;

public class EntityAttributes extends MiddleEntityAttributes {

	public EntityAttributes(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData entityattributes = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_ATTRIBUTES);
		GenericSkippingTable<String> table = GenericIdSkipper.ATTRIBUTES.getTable(version);
		ArrayList<Attribute> sendattrs = new ArrayList<>();
		for (Attribute attribute : attributes.values()) {
			if (!table.shouldSkip(attribute.key)) {
				sendattrs.add(attribute);
			}
		}
		entityattributes.writeInt(entityId);
		entityattributes.writeInt(sendattrs.size());
		for (Attribute attribute : sendattrs) {
			StringSerializer.writeString(entityattributes, version, LegacyEntityAttribute.getLegacyId(attribute.key));
			entityattributes.writeDouble(attribute.value);
			if (version != ProtocolVersion.MINECRAFT_1_6_1) {
				entityattributes.writeShort(attribute.modifiers.length);
				for (Modifier modifier : attribute.modifiers) {
					UUIDSerializer.writeUUID2L(entityattributes, modifier.uuid);
					entityattributes.writeDouble(modifier.amount);
					entityattributes.writeByte(modifier.operation);
				}
			}
		}
		codec.write(entityattributes);
	}

}
