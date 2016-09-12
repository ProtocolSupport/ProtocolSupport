package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8;

import java.io.IOException;
import java.util.ArrayList;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.typeskipper.string.SkippingTable;
import protocolsupport.protocol.typeskipper.string.StringSkipper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntitySetAttributes extends MiddleEntitySetAttributes<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_ATTRIBUTES_ID, version);
		SkippingTable table = StringSkipper.ATTRIBUTES.getTable(version);
		ArrayList<Attribute> sendattrs = new ArrayList<Attribute>();
		for (Attribute attribute : attributes) {
			if (!table.shouldSkip(attribute.key)) {
				sendattrs.add(attribute);
			}
		}
		serializer.writeVarInt(entityId);
		serializer.writeInt(sendattrs.size());
		for (Attribute attribute : sendattrs) {
			serializer.writeString(attribute.key);
			serializer.writeDouble(attribute.value);
			serializer.writeVarInt(attribute.modifiers.length);
			for (Modifier modifier : attribute.modifiers) {
				serializer.writeUUID(modifier.uuid);
				serializer.writeDouble(modifier.amount);
				serializer.writeByte(modifier.operation);
			}
		}
		return RecyclableSingletonList.create(serializer);
	}

}
