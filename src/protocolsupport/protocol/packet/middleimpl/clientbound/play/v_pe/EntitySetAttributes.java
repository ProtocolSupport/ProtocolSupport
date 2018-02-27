package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.util.ArrayList;
import java.util.HashMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntitySetAttributes extends MiddleEntitySetAttributes {

	private static final HashMap<String, String> remapAttrNames = new HashMap<>();
	private static final ArrayList<String> forbiddenAttrNames = new ArrayList<>();
	private static final HashMap<String, float[]> knownMinMax = new HashMap<>();
	static {
		remapAttrNames.put("horse.jumpStrength", 			"minecraft:horse.jump_strength");
		remapAttrNames.put("generic.movementSpeed", 		"minecraft:movement");
		remapAttrNames.put("generic.attackDamage", 			"minecraft:attack_damage");
		remapAttrNames.put("generic.knockbackResistance", 	"minecraft:knockback_resistance");
		remapAttrNames.put("generic.followRange", 			"minecraft:follow_range");
		//Min max values.												MIN   DEFAULT				MAX
		knownMinMax.put("minecraft:horse.jump_strength", 	new float[]{0.0F, 0.432084373616155F, 	 2.0F});
		knownMinMax.put("minecraft:health", 				new float[]{0.0F, 20.0F,				20.0F});
		knownMinMax.put("minecraft:player.saturation", 		new float[]{0.0F, 20.0F,				20.0F});
		knownMinMax.put("minecraft:player.hunger", 			new float[]{0.0F, 20.0F,				20.0F});
		knownMinMax.put("minecraft:player.experience", 		new float[]{0.0F,  0.0F,				 1.0F});
		knownMinMax.put("minecraft:player.level", 			new float[]{0.0F,  0.0F, 			 24791.0F});
		knownMinMax.put("minecraft:movement", 				new float[]{0.0F,   -1F,			 24791.0F});
		knownMinMax.put("minecraft:attack_damage", 			new float[]{0.0F,  1.0F,				 2.0F});
		knownMinMax.put("minecraft:knockback_resistance", 	new float[]{0.0F,  0.0F,			  2080.0F});
		forbiddenAttrNames.add("generic.maxHealth"); //Is used instead as max value for the health attribute.
		forbiddenAttrNames.add("generic.attackSpeed");
		forbiddenAttrNames.add("generic.armor");
		forbiddenAttrNames.add("generic.armorToughness");
		forbiddenAttrNames.add("generic.luck");
		forbiddenAttrNames.add("generic.flyingSpeed");
		forbiddenAttrNames.add("zombie.spawnReinforcements");
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntity entity = cache.getWatchedEntity(entityId);
		if (entity != null) {
			return RecyclableSingletonList.create(create(connection.getVersion(), entity, 
					attributes.values().stream().filter(attr -> !forbiddenAttrNames.contains(attr.key)).toArray(Attribute[]::new)
				));
		}
		return RecyclableEmptyList.get();
	}

	public static ClientBoundPacketData create(ProtocolVersion version, NetworkEntity entity, Attribute... attributes) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SET_ATTRIBUTES, version);
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
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
			float[] minmax = knownMinMax.getOrDefault(pename, new float[]{0, 1, Float.MAX_VALUE});
			if (pename.equals("minecraft:health")) { minmax[2] = entity.getDataCache().getMaxHealth(); } //Max health via health attribute.
			serializer.writeFloatLE(minmax[0]); //Min
			serializer.writeFloatLE(minmax[2]); //Max
			serializer.writeFloatLE((float) attrvalue); //Value
			serializer.writeFloatLE(minmax[1] == -1F ? (float) attrvalue : minmax[1]); //default value
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
