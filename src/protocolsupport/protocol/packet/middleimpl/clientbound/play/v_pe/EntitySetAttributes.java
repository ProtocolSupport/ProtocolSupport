package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import java.util.HashMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.utils.ObjectFloatTuple;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntitySetAttributes extends MiddleEntitySetAttributes {

	public EntitySetAttributes(ConnectionImpl connection) {
		super(connection);
	}

	public static enum AttributeInfo {

		HEALTH("minecraft:health", 0.0F, 20.0F, 20.0F),
		MOVE_SPEED("minecraft:movement", 0.0F, -1F, 24791.0F),
		ATTACK_DAMAGE("minecraft:attack_damage", 0.0F, 1.0F, 2.0F),
		KNOCKBACK_RESISTANCE("minecraft:knockback_resistance", 0.0F, 0.0F, 2080.0F),
		PLAYER_SATURATION("minecraft:player.saturation", 0.0F, 20.0F, 20.0F),
		PLAYER_HUNGER("minecraft:player.hunger", 0.0F, 20.0F, 20.0F),
		PLAYER_EXPERIENCE("minecraft:player.experience", 0.0F, 0.0F, 1.0F),
		PLAYER_LEVEL("minecraft:player.level", 0.0F, 0.0F, 24791.0F),
		HORSE_JUMP_STRENGTH("minecraft:horse.jump_strength", 0.0F, 0.432084373616155F, 2.0F);

		protected final String name;
		protected final float minValue;
		protected final float defaultValue;
		protected final float maxValue;

		AttributeInfo(String name, float minValue, float defaultValue, float maxValue) {
			this.name = name;
			this.minValue = minValue;
			this.defaultValue = defaultValue;
			this.maxValue = maxValue;
		}

		public String getName() {
			return name;
		}

		public float getMinValue() {
			return minValue;
		}

		public float getDefaultValue() {
			return defaultValue;
		}

		public float getMaxValue() {
			return maxValue;
		}
	}

	protected static final HashMap<String, AttributeInfo> remapAttrs = new HashMap<>();

	static {
		remapAttrs.put("generic.movementSpeed", AttributeInfo.MOVE_SPEED);
		remapAttrs.put("generic.attackDamage", AttributeInfo.ATTACK_DAMAGE);
		remapAttrs.put("generic.knockbackResistance", AttributeInfo.KNOCKBACK_RESISTANCE);
//		remapAttrs.put("generic.followRange", "minecraft:follow_range"); //TODO: find out min, max, default
		remapAttrs.put("horse.jumpStrength", AttributeInfo.HORSE_JUMP_STRENGTH);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		NetworkEntity entity = cache.getWatchedEntityCache().getWatchedEntity(entityId);
		if (entity != null) {
			return RecyclableSingletonList.create(create(
				connection.getVersion(), entity,
				attributes.values().stream()
					.map(attr -> new ObjectFloatTuple<>(remapAttrs.get(attr.key), calcAttrValue(attr.value, attr.modifiers)))
					.filter(tuple -> tuple.getObject() != null)
					.toArray(ObjectFloatTuple[]::new)
			));
		}
		return RecyclableEmptyList.get();
	}

	protected static float calcAttrValue(double value, Modifier[] modifiers) {
		double add = 0;
		double mulInc = 1;
		double mulMore = 1;
		for (Modifier modifier : modifiers) {
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
		return (float) ((value + add) * mulInc * mulMore);
	}

	@SafeVarargs
	public static ClientBoundPacketData create(ProtocolVersion version, NetworkEntity entity, ObjectFloatTuple<AttributeInfo>... attributes) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SET_ATTRIBUTES);
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		VarNumberSerializer.writeVarInt(serializer, attributes.length);
		for (ObjectFloatTuple<AttributeInfo> attr : attributes) {
			AttributeInfo attrInfo = attr.getObject();
			serializer.writeFloatLE(attr.getObject().getMinValue());
			if (attrInfo == AttributeInfo.HEALTH) {
				serializer.writeFloatLE(entity.getDataCache().getMaxHealth());
				float health = attr.getFloat();
				serializer.writeFloatLE((health > 0) && (health < 1) ? 1 : health);
			} else {
				serializer.writeFloatLE(attrInfo.getMaxValue());
				serializer.writeFloatLE(attr.getFloat());
			}
			if (attrInfo == AttributeInfo.MOVE_SPEED) {
				serializer.writeFloatLE(attr.getFloat());
			} else {
				serializer.writeFloatLE(attrInfo.getDefaultValue());
			}
			StringSerializer.writeString(serializer, version, attrInfo.getName());
		}
		return serializer;
	}

}
