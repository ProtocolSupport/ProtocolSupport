package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2;

import java.util.ArrayList;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntitySetAttributes extends MiddleEntitySetAttributes {

	public EntitySetAttributes(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_ATTRIBUTES);
		GenericSkippingTable<String> table = GenericIdSkipper.ATTRIBUTES.getTable(version);
		ArrayList<Attribute> sendattrs = new ArrayList<>();
		for (Attribute attribute : attributes.values()) {
			if (!table.shouldSkip(attribute.key)) {
				sendattrs.add(attribute);
			}
		}
		VarNumberSerializer.writeVarInt(serializer, entityId);
		serializer.writeInt(sendattrs.size());
		for (Attribute attribute : sendattrs) {
			StringSerializer.writeVarIntUTF8String(serializer, attribute.key);
			serializer.writeDouble(attribute.value);
			VarNumberSerializer.writeVarInt(serializer, attribute.modifiers.length);
			for (Modifier modifier : attribute.modifiers) {
				MiscSerializer.writeUUID(serializer, modifier.uuid);
				serializer.writeDouble(modifier.amount);
				serializer.writeByte(modifier.operation);
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
