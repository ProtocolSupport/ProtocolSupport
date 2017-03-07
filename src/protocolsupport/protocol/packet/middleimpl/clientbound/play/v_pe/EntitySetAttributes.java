package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.util.HashMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntitySetAttributes extends MiddleEntitySetAttributes {

	private static final HashMap<String, String> attrNames = new HashMap<>();
	static {
		attrNames.put("generic.movementSpeed", "minecraft:movement");
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		return RecyclableSingletonList.create(create(version, entityId, attributes.values().toArray(new Attribute[0])));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, int entityId, Attribute... attributes) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SET_ATTRIBUTES, version);
		VarNumberSerializer.writeSVarInt(serializer, entityId);
		VarNumberSerializer.writeVarInt(serializer, attributes.length);
		for (Attribute attr : attributes) {
			MiscSerializer.writeLFloat(serializer, Float.MIN_VALUE); //min value
			MiscSerializer.writeLFloat(serializer, Float.MAX_VALUE); //max value
			MiscSerializer.writeLFloat(serializer, (float) attr.value);
			MiscSerializer.writeLFloat(serializer, 0.000001F); //default value
			StringSerializer.writeString(serializer, version, attrNames.getOrDefault(attr.key, attr.key));
		}
		return serializer;
	}

}
