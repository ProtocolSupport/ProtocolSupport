package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.util.HashMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.Any;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntitySetAttributes extends MiddleEntitySetAttributes {

	private static final HashMap<String, String> remapAttrNames = new HashMap<>();
	private static final HashMap<String, Any<Float, Float>> knownMinMax = new HashMap<>();
	static {
		remapAttrNames.put("generic.movementSpeed", "minecraft:movement");
		knownMinMax.put("minecraft:health", new Any<Float, Float>(0.0F, 20.0F));
		knownMinMax.put("minecraft:player.saturation", new Any<Float, Float>(0.0F, 20.0F));
		knownMinMax.put("minecraft:player.hunger", new Any<Float, Float>(0.0F, 20.0F));
		knownMinMax.put("minecraft:player.experience", new Any<Float, Float>(0.0F, 1.0F));
		knownMinMax.put("minecraft:player.level", new Any<Float, Float>(0.0F, 24791.0F));
		knownMinMax.put("minecraft:movement", new Any<Float, Float>(0.0F, 24791.0F));
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
			double add = 0;
			double mulInc = 1;
			double mulMore = 1;
			for (Modifier modifier : attr.modifiers) {
				switch (modifier.operation) {
					case 0: {
						add += modifier.amount;
						break;
					}
					case 1: {
						mulInc += modifier.amount;
						break;
					}
					case 2: {
						mulMore *= (modifier.amount + 1);
						break;
					}
				}
			}
			double attrvalue = (attr.value + add) * mulInc * mulMore;
			String pename = remapAttrNames.getOrDefault(attr.key, attr.key);
			Any<Float, Float> minmax = knownMinMax.getOrDefault(pename, new Any<Float, Float>(Float.MIN_VALUE, Float.MAX_VALUE));
			MiscSerializer.writeLFloat(serializer, minmax.getObj1());
			MiscSerializer.writeLFloat(serializer, minmax.getObj2());
			MiscSerializer.writeLFloat(serializer, (float) attrvalue);
			MiscSerializer.writeLFloat(serializer, 0.000001F); //default value
			StringSerializer.writeString(serializer, version, pename);
		}
		return serializer;
	}

	public static Attribute createAttribute(String name, double value) {
		Attribute attr = new Attribute();
		attr.key = name;
		attr.value = value;
		attr.modifiers = new Modifier[0];
		return attr;
	}

}
