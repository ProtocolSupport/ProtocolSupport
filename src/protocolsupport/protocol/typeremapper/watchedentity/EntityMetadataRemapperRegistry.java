package protocolsupport.protocol.typeremapper.watchedentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.block.BlockIdRemappingHelper;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.typeremapper.particle.legacy.LegacyParticle;
import protocolsupport.protocol.typeremapper.watchedentity.value.IndexValueRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.value.IndexValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.watchedentity.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.watchedentity.value.IndexValueRemapperNumberToByte;
import protocolsupport.protocol.typeremapper.watchedentity.value.IndexValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.watchedentity.value.IndexValueRemapperNumberToShort;
import protocolsupport.protocol.typeremapper.watchedentity.value.IndexValueRemapperOptionalChatToString;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.particle.Particle;
import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.Utils;

public enum EntityMetadataRemapperRegistry {

	NONE(NetworkEntityType.NONE),
	// Mobs
	ENTITY(NetworkEntityType.ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.FLAGS, 0), ProtocolVersionsHelper.ALL_PC),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.RANGE__1_9__1_13),
		new Entry(new IndexValueRemapperNumberToShort(DataWatcherObjectIndex.Entity.AIR, 1), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperOptionalChatToString(DataWatcherObjectIndex.Entity.NAMETAG, 2, 64), ProtocolVersionsHelper.RANGE__1_9__1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.NAMETAG, 2), ProtocolVersionsHelper.AFTER_1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 3), ProtocolVersionsHelper.RANGE__1_9__1_13),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.SILENT, 4), ProtocolVersionsHelper.RANGE__1_9__1_13),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Entity.NO_GRAVITY, 5), ProtocolVersionsHelper.RANGE__1_10__1_13_1)
	),
	LIVING(NetworkEntityType.LIVING, ENTITY,
		new Entry(new IndexValueRemapperOptionalChatToString(DataWatcherObjectIndex.Entity.NAMETAG, 2, 64), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapperOptionalChatToString(DataWatcherObjectIndex.Entity.NAMETAG, 10, 64), ProtocolVersionsHelper.RANGE__1_6__1_7),
		new Entry(new IndexValueRemapperOptionalChatToString(DataWatcherObjectIndex.Entity.NAMETAG, 5, 64), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 3), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 11), ProtocolVersionsHelper.RANGE__1_6__1_7),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Entity.NAMETAG_VISIBLE, 6), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.HAND_USE, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.HAND_USE, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.HEALTH, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.HEALTH, 6), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 7), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 7), ProtocolVersionsHelper.RANGE__1_6__1_8),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.EntityLiving.POTION_COLOR, 8), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 9), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 8), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 8), ProtocolVersionsHelper.RANGE__1_6__1_8),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.EntityLiving.POTION_AMBIENT, 9), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 10), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 9), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 9), ProtocolVersionsHelper.RANGE__1_6__1_8),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.EntityLiving.ARROWS_IN, 10), ProtocolVersionsHelper.BEFORE_1_6)
	),
	INSENTIENT(NetworkEntityType.INSENTIENT, LIVING,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Insentient.FLAGS, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Insentient.FLAGS, 10), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Insentient.FLAGS, 15), ProtocolVersion.MINECRAFT_1_8)
	),
	PLAYER(NetworkEntityType.PLAYER, LIVING,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.ADDITIONAL_HEARTS, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.ADDITIONAL_HEARTS, 10), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.ADDITIONAL_HEARTS, 17), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SCORE, 12),  ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SCORE, 11),  ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Player.SCORE, 18),  ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SKIN_FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SKIN_FLAGS, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.SKIN_FLAGS, 10), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.MAIN_HAND, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.MAIN_HAND, 13), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.LEFT_SHOULDER_ENTITY, 15), ProtocolVersionsHelper.RANGE__1_12__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Player.RIGHT_SHOULDER_ENTITY, 16), ProtocolVersionsHelper.RANGE__1_12__1_13_1),
		new Entry(new DataWatcherObjectRemapper() {
			@Override
			public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
				DataWatcherObjectIndex.Entity.FLAGS.getValue(original)
				.ifPresent(baseflags -> entity.getDataCache().setBaseFlags(baseflags.getValue()));
				DataWatcherObjectIndex.EntityLiving.HAND_USE.getValue(original)
				.ifPresent(activehandflags -> {
					entity.getDataCache().setBaseFlag(5, activehandflags.getValue());
					remapped.put(0, new DataWatcherObjectByte(entity.getDataCache().getBaseFlags()));
				});
			}
		}, ProtocolVersionsHelper.BEFORE_1_9)
	),
	AGEABLE(NetworkEntityType.AGEABLE, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ageable.IS_BABY, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ageable.IS_BABY, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ageable.IS_BABY, 12) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte((byte) (object.getValue() ? -1 : 0));
			}
		}, ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Ageable.IS_BABY, 12) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectInt((object.getValue() ? -1 : 0));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Ageable.AGE_HACK, 12), ProtocolVersionsHelper.RANGE__1_6__1_7)
	),
	TAMEABLE(NetworkEntityType.TAMEABLE, AGEABLE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tameable.TAME_FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tameable.TAME_FLAGS, 12),ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tameable.TAME_FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	ARMOR_STAND(NetworkEntityType.ARMOR_STAND, LIVING,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.FLAGS, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.FLAGS, 10), ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.HEAD_ROT, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.HEAD_ROT, 11), ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.BODY_ROT, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.BODY_ROT, 12), ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.LEFT_ARM_ROT, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.LEFT_ARM_ROT, 13), ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.RIGHT_ARM_ROT, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.RIGHT_ARM_ROT, 14), ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.LEFT_LEG_ROT, 16), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.LEFT_LEG_ROT, 15), ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.RIGHT_LEG_ROT, 17), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ArmorStand.RIGHT_LEG_ROT, 16), ProtocolVersionsHelper.RANGE__1_8__1_9)
	),
	COW(NetworkEntityType.COW, AGEABLE),
	MUSHROOM_COW(NetworkEntityType.MUSHROOM_COW, COW),
	CHICKEN(NetworkEntityType.CHICKEN, AGEABLE),
	SQUID(NetworkEntityType.SQUID, INSENTIENT),
	BASE_HORSE(NetworkEntityType.BASE_HORSE, AGEABLE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BaseHorse.FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BaseHorse.FLAGS, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.BaseHorse.FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	BATTLE_HORSE(NetworkEntityType.BATTLE_HORSE, BASE_HORSE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.VARIANT, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.VARIANT, 14), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.BattleHorse.VARIANT, 20), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.ARMOR, 16), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.ARMOR, 17), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BattleHorse.ARMOR, 16), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.BattleHorse.ARMOR, 22), ProtocolVersionsHelper.BEFORE_1_9)
	),
	CARGO_HORSE(NetworkEntityType.CARGO_HORSE, BASE_HORSE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.CargoHorse.HAS_CHEST, 15), ProtocolVersionsHelper.RANGE__1_11__1_13_1)
	),
	COMMON_HORSE(NetworkEntityType.COMMON_HORSE, BATTLE_HORSE),
	ZOMBIE_HORSE(NetworkEntityType.ZOMBIE_HORSE, BATTLE_HORSE,
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(14, new DataWatcherObjectVarInt(3)), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(13, new DataWatcherObjectVarInt(3)), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(19, new DataWatcherObjectByte((byte) 3)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8))
	),
	SKELETON_HORSE(NetworkEntityType.SKELETON_HORSE, BATTLE_HORSE,
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(14, new DataWatcherObjectVarInt(4)), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(13, new DataWatcherObjectVarInt(4)), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(19, new DataWatcherObjectByte((byte) 4)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8))
	),
	DONKEY(NetworkEntityType.DONKEY, CARGO_HORSE,
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(14, new DataWatcherObjectVarInt(1)), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(13, new DataWatcherObjectVarInt(1)), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(19, new DataWatcherObjectByte((byte) 1)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8))
	),
	MULE(NetworkEntityType.MULE, CARGO_HORSE,
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(14, new DataWatcherObjectVarInt(2)), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(13, new DataWatcherObjectVarInt(2)), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(19, new DataWatcherObjectByte((byte) 2)), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_6_1, ProtocolVersion.MINECRAFT_1_8))
	),
	LAMA(NetworkEntityType.LAMA, CARGO_HORSE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Lama.STRENGTH, 16), ProtocolVersionsHelper.RANGE__1_11__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Lama.CARPET_COLOR, 17), ProtocolVersionsHelper.RANGE__1_11__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Lama.VARIANT, 18), ProtocolVersionsHelper.RANGE__1_11__1_13_1)
	),
	BAT(NetworkEntityType.BAT, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Bat.HANGING, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Bat.HANGING, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Bat.HANGING, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	OCELOT(NetworkEntityType.OCELOT, TAMEABLE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ocelot.VARIANT, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ocelot.VARIANT, 14), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Ocelot.VARIANT, 18), ProtocolVersionsHelper.BEFORE_1_9)
	),
	WOLF(NetworkEntityType.WOLF, TAMEABLE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.HEALTH, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.HEALTH, 14), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.HEALTH, 18), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wolf.HEALTH, 18), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.BEGGING, 16), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.BEGGING, 15), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Wolf.BEGGING, 19), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 17), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 16), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 20), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Wolf.COLLAR_COLOR, 20) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (15 - object.getValue()));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8)
	),
	PIG(NetworkEntityType.PIG, AGEABLE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Pig.HAS_SADLLE, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Pig.HAS_SADLLE, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Pig.HAS_SADLLE, 16), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Pig.BOOST_TIME, 14), ProtocolVersionsHelper.RANGE__1_11_1__1_13_1)
	),
	RABBIT(NetworkEntityType.RABBIT, AGEABLE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Rabbit.VARIANT, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Rabbit.VARIANT, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Rabbit.VARIANT, 18), ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHEEP(NetworkEntityType.SHEEP, AGEABLE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Sheep.FLAGS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Sheep.FLAGS, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Sheep.FLAGS, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	POLAR_BEAR(NetworkEntityType.POLAR_BEAR, AGEABLE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.PolarBear.STANDING_UP, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1)
	),
	VILLAGER(NetworkEntityType.VILLAGER, AGEABLE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Villager.PROFESSION, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Villager.PROFESSION, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Villager.PROFESSION, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	ENDERMAN(NetworkEntityType.ENDERMAN, INSENTIENT,
//TODO: Remap this.
//		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 12), ProtocolVersionsHelper.RANGE__1_10__1_13),
//		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 11), ProtocolVersionsHelper.ALL_1_9),
//		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 16) {
//			@Override
//			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockState object) {
//				return new DataWatcherObjectShort((short) MinecraftData.getBlockIdFromState(object.getValue()));
//			}
//		}, ProtocolVersion.MINECRAFT_1_8),
//		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 16) {
//			@Override
//			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockState object) {
//				return new DataWatcherObjectByte((byte) MinecraftData.getBlockIdFromState(object.getValue()));
//			}
//		}, ProtocolVersionsHelper.BEFORE_1_8),
//		new Entry(new IndexValueRemapper<Integer, DataWatcherObjectBlockState>(DataWatcherObjectIndex.Enderman.CARRIED_BLOCK, 17) {
//			@Override
//			public DataWatcherObject<?> remapValue(DataWatcherObjectBlockState object) {
//				return new DataWatcherObjectByte((byte) MinecraftData.getBlockDataFromState(object.getValue()));
//			}
//		}, ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.SCREAMING, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Enderman.SCREAMING, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Enderman.SCREAMING, 18), ProtocolVersionsHelper.BEFORE_1_9)
	),
	GIANT(NetworkEntityType.GIANT, INSENTIENT),
	SILVERFISH(NetworkEntityType.SILVERFISH, INSENTIENT),
	ENDERMITE(NetworkEntityType.ENDERMITE, INSENTIENT),
	ENDER_DRAGON(NetworkEntityType.ENDER_DRAGON, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EnderDragon.PHASE, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EnderDragon.PHASE, 11), ProtocolVersionsHelper.ALL_1_9)
	),
	SNOWMAN(NetworkEntityType.SNOWMAN, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Snowman.NO_HAT, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Snowman.NO_HAT, 11), ProtocolVersionsHelper.ALL_1_9)
	),
	ZOMBIE(NetworkEntityType.ZOMBIE, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.BABY, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.BABY, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Zombie.BABY, 12), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.HANDS_UP, 14), ProtocolVersionsHelper.RANGE__1_11__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.HANDS_UP, 15), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Zombie.HANDS_UP, 14), ProtocolVersionsHelper.ALL_1_9)
	),
	ZOMBIE_VILLAGER(NetworkEntityType.ZOMBIE_VILLAGER, ZOMBIE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ZombieVillager.CONVERTING, 15), ProtocolVersionsHelper.RANGE__1_11__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ZombieVillager.CONVERTING, 14), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ZombieVillager.CONVERTING, 13), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.ZombieVillager.CONVERTING, 14), ProtocolVersionsHelper.BEFORE_1_9)
	),
	HUSK(NetworkEntityType.HUSK, ZOMBIE),
	ZOMBIE_PIGMAN(NetworkEntityType.ZOMBIE_PIGMAN, ZOMBIE),
	BLAZE(NetworkEntityType.BLAZE, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Blaze.ON_FIRE, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Blaze.ON_FIRE, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Blaze.ON_FIRE, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPIDER(NetworkEntityType.SPIDER, LIVING,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Spider.CLIMBING, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Spider.CLIMBING, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Spider.CLIMBING, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	CAVE_SPIDER(NetworkEntityType.CAVE_SPIDER, SPIDER),
	CREEPER(NetworkEntityType.CREEPER, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Creeper.STATE, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Creeper.STATE, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Creeper.STATE, 16), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Creeper.POWERED, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Creeper.POWERED, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Creeper.POWERED, 17), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Creeper.IGNITED, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Creeper.IGNITED, 13), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Creeper.IGNITED, 18), ProtocolVersionsHelper.BEFORE_1_9)
	),
	GHAST(NetworkEntityType.GHAST, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ghast.ATTACKING, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Ghast.ATTACKING, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Ghast.ATTACKING, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	SLIME(NetworkEntityType.SLIME, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Slime.SIZE, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Slime.SIZE, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.Slime.SIZE, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	MAGMA_CUBE(NetworkEntityType.MAGMA_CUBE, SLIME),
	BASE_SKELETON(NetworkEntityType.BASE_SKELETON, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Skeleton.SWINGING_HANDS, 12), ProtocolVersionsHelper.RANGE__1_11__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Skeleton.SWINGING_HANDS, 13), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Skeleton.SWINGING_HANDS, 12), ProtocolVersionsHelper.ALL_1_9)
	),
	SKELETON(NetworkEntityType.SKELETON, BASE_SKELETON),
	WITHER_SKELETON(NetworkEntityType.WITHER_SKELETON, BASE_SKELETON,
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(12, new DataWatcherObjectVarInt(1)), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(11, new DataWatcherObjectVarInt(1)), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(13, new DataWatcherObjectByte((byte) 1)), ProtocolVersionsHelper.BEFORE_1_9)
	),
	STRAY(NetworkEntityType.STRAY, BASE_SKELETON,
		new Entry(new FirstDataWatcherUpdateObjectAddRemapper(12, new DataWatcherObjectVarInt(2)), ProtocolVersion.MINECRAFT_1_10)
	),
	WITCH(NetworkEntityType.WITCH, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Witch.DRINKING_POTION, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Witch.DRINKING_POTION, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Witch.DRINKING_POTION, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	IRON_GOLEM(NetworkEntityType.IRON_GOLEM, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.IronGolem.PLAYER_CREATED, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.IronGolem.PLAYER_CREATED, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.IronGolem.PLAYER_CREATED, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHULKER(NetworkEntityType.SHULKER, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.DIRECTION, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.DIRECTION, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.ATTACHMENT_POS, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.ATTACHMENT_POS, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.SHIELD_HEIGHT, 13), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Shulker.COLOR, 15), ProtocolVersionsHelper.RANGE__1_11__1_13_1)
	),
	WITHER(NetworkEntityType.WITHER, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET1, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET1, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET1, 17), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET2, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET2, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET2, 18), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET3, 14), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.TARGET3, 13), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.TARGET3, 19), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 15), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 14), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Wither.INVULNERABLE_TIME, 20), ProtocolVersionsHelper.BEFORE_1_9)
	),
	GUARDIAN(NetworkEntityType.GUARDIAN, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Guardian.SPIKES, 12), ProtocolVersionsHelper.RANGE__1_11__1_13_1),
		new Entry(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Guardian.SPIKES, 12) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Guardian.SPIKES, 11) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapper<DataWatcherObjectBoolean>(DataWatcherObjectIndex.Guardian.SPIKES, 16) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectInt(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Guardian.TARGET_ID, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Guardian.TARGET_ID, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Guardian.TARGET_ID, 17), ProtocolVersion.MINECRAFT_1_8)
	),
	ELDER_GUARDIAN(NetworkEntityType.ELDER_GUARDIAN, GUARDIAN),
	VINDICATOR(NetworkEntityType.VINDICATOR, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Vindicator.HAS_TARGET, 12), ProtocolVersionsHelper.RANGE__1_11__1_13_1)
	),
	EVOKER(NetworkEntityType.EVOKER, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Evoker.SPELL, 12), ProtocolVersionsHelper.RANGE__1_11__1_13_1)
	),
	ILLUSIONER(NetworkEntityType.ILLUSIONER, EVOKER),
	VEX(NetworkEntityType.VEX, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Vex.FLAGS, 12), ProtocolVersionsHelper.RANGE__1_11__1_13_1)
	),
	PARROT(NetworkEntityType.PARROT, TAMEABLE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Parrot.VARIANT, 15), ProtocolVersionsHelper.RANGE__1_12__1_13_1)
	),
	ARMOR_STAND_MOB(NetworkEntityType.ARMOR_STAND_MOB, ARMOR_STAND),
	//TODO Remap these better for old version? Hand over some meta? Eg slime size for phantom or so.
	PHANTOM(NetworkEntityType.PHANTOM, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Phantom.SIZE, 12), ProtocolVersionsHelper.AFTER_1_12_2)
	),
	DOLPHIN(NetworkEntityType.DOLPHIN, INSENTIENT),
	BASE_FISH(NetworkEntityType.BASE_FISH, INSENTIENT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.BaseFish.FROM_BUCKET, 12), ProtocolVersionsHelper.AFTER_1_12_2)
	),
	COD(NetworkEntityType.COD, BASE_FISH),
	PUFFERFISH(NetworkEntityType.PUFFERFISH, BASE_FISH,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.PufferFish.PUFF_STATE, 13), ProtocolVersionsHelper.AFTER_1_12_2)
	),
	SALMON(NetworkEntityType.SALMON, BASE_FISH),
	TROPICAL_FISH(NetworkEntityType.TROPICAL_FISH, BASE_FISH,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.TropicalFish.VARIANT, 13), ProtocolVersionsHelper.AFTER_1_12_2)
	),
	TURTLE(NetworkEntityType.TURTLE, AGEABLE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Turtle.HOME_POS, 13), ProtocolVersionsHelper.AFTER_1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Turtle.HAS_EGG, 14), ProtocolVersionsHelper.AFTER_1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Turtle.LAYING_EGG, 15), ProtocolVersionsHelper.AFTER_1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Turtle.TRAVEL_POS, 16), ProtocolVersionsHelper.AFTER_1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Turtle.GOING_HOME, 17), ProtocolVersionsHelper.AFTER_1_12_2),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Turtle.TRAVELING, 18), ProtocolVersionsHelper.AFTER_1_12_2)
	),
	DROWNED(NetworkEntityType.DROWNED, ZOMBIE,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Drowned.HAS_TARGET, 15), ProtocolVersionsHelper.AFTER_1_12_2)
	),
	// Objects
	BOAT(NetworkEntityType.BOAT,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.TIME_SINCE_LAST_HIT, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.TIME_SINCE_LAST_HIT, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Boat.TIME_SINCE_LAST_HIT, 17), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.FORWARD_DIRECTION, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.FORWARD_DIRECTION, 6), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Boat.FORWARD_DIRECTION, 18), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 7), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 19), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Boat.DAMAGE_TAKEN, 19), ProtocolVersionsHelper.BEFORE_1_6),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.VARIANT, 9), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.LEFT_PADDLE, 10), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.RIGHT_PADDLE, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Boat.SPLASH_TIMER, 12), ProtocolVersionsHelper.AFTER_1_12_2)
	),
	TNT(NetworkEntityType.TNT, ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tnt.FUSE, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Tnt.FUSE, 5), ProtocolVersionsHelper.ALL_1_9)
	),
	SNOWBALL(NetworkEntityType.SNOWBALL, ENTITY),
	EGG(NetworkEntityType.EGG, ENTITY),
	FIREBALL(NetworkEntityType.FIREBALL, ENTITY),
	FIRECHARGE(NetworkEntityType.FIRECHARGE, ENTITY),
	ENDERPEARL(NetworkEntityType.ENDERPEARL, ENTITY),
	WITHER_SKULL(NetworkEntityType.WITHER_SKULL, FIREBALL,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.WitherSkull.CHARGED, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.WitherSkull.CHARGED, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.WitherSkull.CHARGED, 10), ProtocolVersionsHelper.BEFORE_1_9)
	),
	FALLING_OBJECT(NetworkEntityType.FALLING_OBJECT, ENTITY),
	ENDEREYE(NetworkEntityType.ENDEREYE, ENTITY),
	POTION(NetworkEntityType.POTION, ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Potion.ITEM, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Potion.ITEM, 7), ProtocolVersion.MINECRAFT_1_10),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Potion.ITEM, 5), ProtocolVersionsHelper.ALL_1_9)
	),
	EXP_BOTTLE(NetworkEntityType.EXP_BOTTLE, ENTITY),
	LEASH_KNOT(NetworkEntityType.LEASH_KNOT, ENTITY),
	FISHING_FLOAT(NetworkEntityType.FISHING_FLOAT, ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.FishingFloat.HOOKED_ENTITY, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.FishingFloat.HOOKED_ENTITY, 5), ProtocolVersionsHelper.ALL_1_9)
	),
	ITEM(NetworkEntityType.ITEM, ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Item.ITEM, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Item.ITEM, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Item.ITEM, 10), ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART(NetworkEntityType.MINECART, ENTITY, Utils.concatArrays(
		new Entry[] {
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHAKING_POWER, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHAKING_POWER, 5), ProtocolVersionsHelper.ALL_1_9),
			new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.SHAKING_POWER, 17), ProtocolVersionsHelper.BEFORE_1_9),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHAKING_DIRECTION, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHAKING_DIRECTION, 6), ProtocolVersionsHelper.ALL_1_9),
			new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.SHAKING_DIRECTION, 18), ProtocolVersionsHelper.BEFORE_1_9),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 7), ProtocolVersionsHelper.ALL_1_9),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 19), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
			new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.DAMAGE_TAKEN, 19), ProtocolVersionsHelper.BEFORE_1_6),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.BLOCK, 9), ProtocolVersionsHelper.AFTER_1_12_2)
		},
		Entry.createPerVersion(
			version ->
				new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK, 9) {
					@Override
					public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
						return new DataWatcherObjectVarInt(BlockIdRemappingHelper.remapToCombinedIdM12(version, object.getValue()));
					}
				},
			ProtocolVersionsHelper.RANGE__1_10__1_12_2
		),
		Entry.createPerVersion(
			version ->
				new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK, 8) {
					@Override
					public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
						return new DataWatcherObjectVarInt(BlockIdRemappingHelper.remapToCombinedIdM12(version, object.getValue()));
					}
				},
			ProtocolVersionsHelper.ALL_1_9
		),
		new Entry[] {
			new Entry(new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK, 20) {
				@Override
				public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
					return new DataWatcherObjectInt(BlockIdRemappingHelper.remapToCombinedIdM12(ProtocolVersion.MINECRAFT_1_8, object.getValue()));
				}
			}, ProtocolVersion.MINECRAFT_1_8),
		},
		Entry.createPerVersion(
			version ->
				new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.Minecart.BLOCK, 20) {
					@Override
					public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
						return new DataWatcherObjectInt(BlockIdRemappingHelper.remapToCombinedIdM16(version, object.getValue()));
					}
				},
			ProtocolVersionsHelper.BEFORE_1_8
		),
		new Entry[] {
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.BLOCK_Y, 10), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.BLOCK_Y, 9), ProtocolVersionsHelper.ALL_1_9),
			new Entry(new IndexValueRemapperNumberToInt(DataWatcherObjectIndex.Minecart.BLOCK_Y, 21), ProtocolVersionsHelper.BEFORE_1_9),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, 11), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, 10), ProtocolVersionsHelper.ALL_1_9),
			new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.Minecart.SHOW_BLOCK, 22), ProtocolVersionsHelper.BEFORE_1_9)
		}
	)),
	MINECART_CHEST(NetworkEntityType.MINECART_CHEST, MINECART),
	MINECART_FURNACE(NetworkEntityType.MINECART_FURNACE, MINECART,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartFurnace.POWERED, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartFurnace.POWERED, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperBooleanToByte(DataWatcherObjectIndex.MinecartFurnace.POWERED, 16), ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART_TNT(NetworkEntityType.MINECART_TNT, MINECART),
	MINECART_SPAWNER(NetworkEntityType.MINECART_MOB_SPAWNER, MINECART),
	MINECART_HOPPER(NetworkEntityType.MINECART_HOPPER, MINECART),
	MINECART_COMMAND(NetworkEntityType.MINECART_COMMAND, MINECART,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.COMMAND, 12), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.COMMAND, 11), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.COMMAND, 23), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_8)),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.LAST_OUTPUT, 13), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.LAST_OUTPUT, 12), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.MinecartCommand.LAST_OUTPUT, 24), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_8))
	),
	ARROW(NetworkEntityType.ARROW, ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Arrow.CIRTICAL, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Arrow.CIRTICAL, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Arrow.CIRTICAL, 15), ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPECTRAL_ARROW(NetworkEntityType.SPECTRAL_ARROW, ARROW),
	TIPPED_ARROW(NetworkEntityType.TIPPED_ARROW, ARROW,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.TippedArrow.COLOR, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.TippedArrow.COLOR, 6), ProtocolVersionsHelper.ALL_1_9)
	),
	THROWN_TRIDENT(NetworkEntityType.THROWN_TRIDENT, ARROW,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Trident.LOYALTY, 7), ProtocolVersionsHelper.AFTER_1_12_2)
	),
	FIREWORK(NetworkEntityType.FIREWORK, ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Firework.ITEM, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Firework.ITEM, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Firework.ITEM, 8), ProtocolVersionsHelper.BEFORE_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.Firework.USER, 7), ProtocolVersionsHelper.RANGE__1_11_1__1_13_1)
	),
	ITEM_FRAME(NetworkEntityType.ITEM_FRAME, ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ItemFrame.ITEM, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ItemFrame.ITEM, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ItemFrame.ITEM, 8), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ItemFrame.ITEM, 2), ProtocolVersionsHelper.BEFORE_1_8),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ItemFrame.ROTATION, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.ItemFrame.ROTATION, 6), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNumberToByte(DataWatcherObjectIndex.ItemFrame.ROTATION, 9), ProtocolVersion.MINECRAFT_1_8),
		new Entry(new IndexValueRemapper<DataWatcherObjectVarInt>(DataWatcherObjectIndex.ItemFrame.ROTATION, 3) {
			@Override
			public DataWatcherObject<?> remapValue(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (object.getValue() >> 1));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8)
	),
	ENDER_CRYSTAL(NetworkEntityType.ENDER_CRYSTAL, ENTITY,
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EnderCrystal.TARGET, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EnderCrystal.TARGET, 5), ProtocolVersionsHelper.ALL_1_9),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EnderCrystal.SHOW_BOTTOM, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
		new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.EnderCrystal.SHOW_BOTTOM, 6), ProtocolVersionsHelper.ALL_1_9)
	),
	ARMOR_STAND_OBJECT(NetworkEntityType.ARMOR_STAND_OBJECT, ARMOR_STAND),
	AREA_EFFECT_CLOUD(NetworkEntityType.AREA_EFFECT_CLOUD, ENTITY, Utils.concatArrays(
		new Entry[] {
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.PARTICLE, 9), ProtocolVersionsHelper.AFTER_1_12_2),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.RADIUS, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.RADIUS, 5), ProtocolVersionsHelper.ALL_1_9),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.COLOR, 7), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.COLOR, 6), ProtocolVersionsHelper.ALL_1_9),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.SINGLE_POINT, 8), ProtocolVersionsHelper.RANGE__1_10__1_13_1),
			new Entry(new IndexValueRemapperNoOp(DataWatcherObjectIndex.AreaEffectCloud.SINGLE_POINT, 7), ProtocolVersionsHelper.ALL_1_9)
		},
		Entry.createPerVersion(
			version ->
				new DataWatcherObjectRemapper() {
					@Override
					public void remap(NetworkEntity entity, ArrayMap<DataWatcherObject<?>> original, ArrayMap<DataWatcherObject<?>> remapped) {
						DataWatcherObjectIndex.AreaEffectCloud.PARTICLE.getValue(original).ifPresent(particleObject -> {
							Particle particle = ParticleRemapper.remap(version, particleObject.getValue());
							remapped.put(9, new DataWatcherObjectVarInt(particle.getId()));
							if (particle instanceof LegacyParticle) {
								LegacyParticle lParticle = (LegacyParticle) particle;
								remapped.put(10, new DataWatcherObjectVarInt(lParticle.getFirstParameter()));
								remapped.put(11, new DataWatcherObjectVarInt(lParticle.getSecondParameter()));
							}
						});
					}
				},
			ProtocolVersionsHelper.RANGE__1_9__1_12_2
		)
	)),
	SHULKER_BULLET(NetworkEntityType.SHULKER_BULLET, ENTITY),
	LAMA_SPIT(NetworkEntityType.LAMA_SPIT, ENTITY),
	DRAGON_FIREBALL(NetworkEntityType.DRAGON_FIREBALL, ENTITY),
	EVOCATOR_FANGS(NetworkEntityType.EVOCATOR_FANGS, ENTITY);

	protected static final Map<NetworkEntityType, EntityMetadataRemapperRegistry> wtype = CollectionsUtils.makeEnumMappingEnumMap(EntityMetadataRemapperRegistry.class, NetworkEntityType.class, (e -> e.type));

	public static EntityMetadataRemapperRegistry fromWatchedType(NetworkEntityType type) {
		return wtype.getOrDefault(type, NONE);
	}

	protected final NetworkEntityType type;
	protected final EnumMap<ProtocolVersion, List<DataWatcherObjectRemapper>> entries = new EnumMap<>(ProtocolVersion.class);

	EntityMetadataRemapperRegistry(NetworkEntityType type, Entry... entries) {
		this.type = type;
		for (Entry entry : entries) {
			for (ProtocolVersion version : entry.versions) {
				this.entries.computeIfAbsent(version, k -> new ArrayList<>()).add(entry.remapper);
			}
		}
	}

	EntityMetadataRemapperRegistry(NetworkEntityType type, EntityMetadataRemapperRegistry superType, Entry... entries) {
		this.type = type;
		for (Map.Entry<ProtocolVersion, List<DataWatcherObjectRemapper>> entry : superType.entries.entrySet()) {
			this.entries.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).addAll(entry.getValue());
		}
		for (Entry entry : entries) {
			for (ProtocolVersion version : entry.versions) {
				this.entries.computeIfAbsent(version, k -> new ArrayList<DataWatcherObjectRemapper>()).add(entry.remapper);
			}
		}
	}

	public List<DataWatcherObjectRemapper> getRemaps(ProtocolVersion version) {
		return entries.getOrDefault(version, Collections.emptyList());
	}

	protected static class Entry {

		public static Entry[] createPerVersion(Function<ProtocolVersion, DataWatcherObjectRemapper> func, ProtocolVersion... versions) {
			return
				Arrays.stream(versions)
				.map(version -> new Entry(func.apply(version), version))
				.collect(Collectors.toList())
				.toArray(new Entry[0]);
		}

		protected final DataWatcherObjectRemapper remapper;
		protected final List<ProtocolVersion> versions;
		public Entry(DataWatcherObjectRemapper remapper, ProtocolVersion... versions) {
			this.remapper = remapper;
			this.versions = Arrays.asList(versions);
		}

	}

}
