package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import java.util.ArrayList;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityAttributes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
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
		VarNumberSerializer.writeVarInt(entityattributes, entityId);
		entityattributes.writeInt(sendattrs.size());
		for (Attribute attribute : sendattrs) {
			StringSerializer.writeVarIntUTF8String(entityattributes, LegacyEntityAttribute.getLegacyId(attribute.key));
			entityattributes.writeDouble(attribute.value);
			VarNumberSerializer.writeVarInt(entityattributes, attribute.modifiers.length);
			for (Modifier modifier : attribute.modifiers) {
				UUIDSerializer.writeUUID2L(entityattributes, modifier.uuid);
				entityattributes.writeDouble(modifier.amount);
				entityattributes.writeByte(modifier.operation);
			}
		}
		codec.write(entityattributes);
	}

}
