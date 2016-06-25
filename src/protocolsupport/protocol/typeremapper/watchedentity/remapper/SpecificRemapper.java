package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.EntityType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperStringClamp;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBlockState;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShort;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperNumberToByte;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperNumberToShort;
import protocolsupport.utils.ProtocolVersionsHelper;

public enum SpecificRemapper {

	NONE(EType.NONE, -1),
	ENTITY(EType.NONE, -1,
		//flags
		new Mapping(0)
		.addRemap(0, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL),
		//air
		new Mapping(1)
		.addRemap(1, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(1, ValueRemapperNumberToShort.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//nametag
		new Mapping(2)
		.addRemap(2, ValueRemapperNoOp.STRING, ProtocolVersionsHelper.ALL_1_9),
		//nametag visible
		new Mapping(3)
		.addRemap(3, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9),
		//silent
		new Mapping(4)
		.addRemap(4, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
	),
	LIVING(EType.NONE, -1, SpecificRemapper.ENTITY,
		//nametag
		new Mapping(2)
		.addRemap(2, ValueRemapperNoOp.STRING, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(10, new ValueRemapperStringClamp(64), ProtocolVersionsHelper.RANGE_1_6_1_7)
		.addRemap(5, new ValueRemapperStringClamp(64), ProtocolVersionsHelper.BEFORE_1_6),
		//nametag visible
		new Mapping(3)
		.addRemap(3, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(11, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.RANGE_1_6_1_7)
		.addRemap(6, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//health
		new Mapping(7)
		.addRemap(6, ValueRemapperNoOp.FLOAT, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_6_1)),
		//pcolor
		new Mapping(8)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(7, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.RANGE_1_6_1_7)
		.addRemap(8, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//pambient
		new Mapping(9)
		.addRemap(8, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(8, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.RANGE_1_6_1_7)
		.addRemap(9, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//arrowsn
		new Mapping(10)
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(9, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.RANGE_1_6_1_7)
		.addRemap(10, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6)
	),
	INSENTIENT(EType.NONE, -1, SpecificRemapper.LIVING,
		//noai
		new Mapping(11)
		.addRemap(10, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(15, ValueRemapperNoOp.BYTE, ProtocolVersion.MINECRAFT_1_8)
	),
	PLAYER(EType.NONE, -1, SpecificRemapper.LIVING,
		//additional hearts
		new Mapping(11)
		.addRemap(10, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.BEFORE_1_9),
		//score
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//skin flags(cape enabled for some protocols)
		new Mapping(13)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(10, ValueRemapperNoOp.BYTE, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1))
	),
	AGEABLE(EType.NONE, -1, SpecificRemapper.INSENTIENT,
		//age
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(12, new ValueRemapper<DataWatcherObjectBoolean>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte((byte) (object.getValue() ? -1 : 0));
			}
		}, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(12, new ValueRemapper<DataWatcherObjectBoolean>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectInt((object.getValue() ? -1 : 0));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8),
		//age - special hack for hologram plugins that want to set int age
		//datawatcher index 30 will be remapped to age datawatcher index
		new Mapping(30)
		.addRemap(12, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.RANGE_1_6_1_7)
	),
	TAMEABLE(EType.NONE, -1, SpecificRemapper.AGEABLE,
		//tame flags
		new Mapping(13)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	ARMOR_STAND(EType.NONE, -1, SpecificRemapper.LIVING,
		//parts position
		new Mapping(11)
		.addRemap(10, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE_1_8_1_9),
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE_1_8_1_9),
		new Mapping(13)
		.addRemap(12, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE_1_8_1_9),
		new Mapping(14)
		.addRemap(13, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE_1_8_1_9),
		new Mapping(15)
		.addRemap(14, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE_1_8_1_9),
		new Mapping(16)
		.addRemap(15, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE_1_8_1_9),
		new Mapping(17)
		.addRemap(16, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE_1_8_1_9)
	),
	COW(EType.MOB, EntityType.COW, SpecificRemapper.AGEABLE),
	MUSHROOM_COW(EType.MOB, EntityType.MUSHROOM_COW, SpecificRemapper.COW),
	CHICKEN(EType.MOB, EntityType.CHICKEN, SpecificRemapper.AGEABLE),
	SQUID(EType.MOB, EntityType.SQUID, SpecificRemapper.INSENTIENT),
	HORSE(EType.MOB, EntityType.HORSE, SpecificRemapper.AGEABLE,
		//info flags
		new Mapping(13)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//type
		new Mapping(14)
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(19, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//color/variant
		new Mapping(15)
		.addRemap(14, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(20, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//armor
		new Mapping(17)
		.addRemap(16, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(22, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	BAT(EType.MOB, EntityType.BAT, SpecificRemapper.INSENTIENT,
		//hanging
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	OCELOT(EType.MOB, EntityType.OCELOT, SpecificRemapper.TAMEABLE,
		//type
		new Mapping(15)
		.addRemap(14, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	WOLF(EType.MOB, EntityType.WOLF, SpecificRemapper.TAMEABLE,
		//health
		new Mapping(15)
		.addRemap(14, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNoOp.FLOAT, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1))
		.addRemap(18, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//begging
		new Mapping(16)
		.addRemap(15, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(19, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//collar color
		new Mapping(17)
		.addRemap(16, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(20, ValueRemapperNumberToByte.INSTANCE, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(20, new ValueRemapper<DataWatcherObjectVarInt>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (15 - object.getValue()));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8)
	),
	PIG(EType.MOB, EntityType.PIG, SpecificRemapper.AGEABLE,
		//has saddle
		new Mapping(13)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	RABBIT(EType.MOB, EntityType.RABBIT, SpecificRemapper.AGEABLE,
		//type
		new Mapping(13)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHEEP(EType.MOB, EntityType.SHEEP, SpecificRemapper.AGEABLE,
		//info flags (color + sheared)
		new Mapping(13)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	VILLAGER(EType.MOB, EntityType.VILLAGER, SpecificRemapper.AGEABLE,
		//profession
		new Mapping(13)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	ENDERMAN(EType.MOB, EntityType.ENDERMAN, SpecificRemapper.INSENTIENT,
		//carried block
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.BLOCKSTATE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, new ValueRemapper<DataWatcherObjectBlockState>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectBlockState object) {
				return new DataWatcherObjectShort((short) (object.getValue() >> 4));
			}
		}, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(16, new ValueRemapper<DataWatcherObjectBlockState>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectBlockState object) {
				return new DataWatcherObjectByte((byte) (object.getValue() >> 4));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8)
		.addRemap(17, new ValueRemapper<DataWatcherObjectBlockState>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectBlockState object) {
				return new DataWatcherObjectByte((byte) (object.getValue() & 0xF));
			}
		}, ProtocolVersionsHelper.BEFORE_1_9),
		//screaming
		new Mapping(13)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	GIANT(EType.MOB, EntityType.GIANT, SpecificRemapper.INSENTIENT),
	SILVERFISH(EType.MOB, EntityType.SILVERFISH, SpecificRemapper.INSENTIENT),
	ENDERMITE(EType.MOB, EntityType.ENDERMITE, SpecificRemapper.INSENTIENT),
	ENDER_DRAGON(EType.MOB, EntityType.ENDER_DRAGON, SpecificRemapper.INSENTIENT),
	SNOWMAN(EType.MOB, EntityType.SNOWMAN, SpecificRemapper.INSENTIENT),
	ZOMBIE(EType.MOB, EntityType.ZOMBIE, SpecificRemapper.INSENTIENT,
		//is baby
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(12, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//profession
		new Mapping(13)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(13, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//is converting
		new Mapping(14)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(14, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	ZOMBIE_PIGMAN(EType.MOB, EntityType.PIG_ZOMBIE, SpecificRemapper.ZOMBIE),
	BLAZE(EType.MOB, EntityType.BLAZE, SpecificRemapper.INSENTIENT,
		//on fire
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPIDER(EType.MOB, EntityType.SPIDER, SpecificRemapper.LIVING,
		//is climbing
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	CAVE_SPIDER(EType.MOB, EntityType.CAVE_SPIDER, SpecificRemapper.SPIDER),
	CREEPER(EType.MOB, EntityType.CREEPER, SpecificRemapper.INSENTIENT,
		//state
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//is powered
		new Mapping(13)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//ignited
		new Mapping(14)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	GHAST(EType.MOB, EntityType.GHAST, SpecificRemapper.INSENTIENT,
		//is attacking
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SLIME(EType.MOB, EntityType.SLIME, SpecificRemapper.INSENTIENT,
		//size
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	MAGMA_CUBE(EType.MOB, EntityType.MAGMA_CUBE, SpecificRemapper.SLIME),
	SKELETON(EType.MOB, EntityType.SKELETON, SpecificRemapper.INSENTIENT,
		//type
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	WITCH(EType.MOB, EntityType.WITCH, SpecificRemapper.INSENTIENT,
		//agressive
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	IRON_GOLEM(EType.MOB, EntityType.IRON_GOLEM, SpecificRemapper.INSENTIENT,
		//player created
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHULKER(EType.MOB, 69, SpecificRemapper.INSENTIENT),
	WITHER(EType.MOB, EntityType.WITHER, SpecificRemapper.INSENTIENT,
		//target 1
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//target 2
		new Mapping(13)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//target 3
		new Mapping(14)
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(19, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//invulnerable time
		new Mapping(15)
		.addRemap(14, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(20, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	GUARDIAN(EType.MOB, EntityType.GUARDIAN, SpecificRemapper.INSENTIENT,
		//info flags(elder, spikes)
		new Mapping(12)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNumberToInt.INSTANCE, ProtocolVersion.MINECRAFT_1_8),
		//target id
		new Mapping(13)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersion.MINECRAFT_1_8)
	),
	ARMOR_STAND_MOB(EType.MOB, EntityType.ARMOR_STAND, SpecificRemapper.ARMOR_STAND),
	BOAT(EType.OBJECT, 1,
		//time since hit
		new Mapping(6)
		.addRemap(5, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//forward direction
		new Mapping(7)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//damage taken
		new Mapping(8)
		.addRemap(7, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(19, ValueRemapperNoOp.FLOAT, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1))
		.addRemap(19, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6)
	),
	TNT(EType.OBJECT, 50, SpecificRemapper.ENTITY),
	SNOWBALL(EType.OBJECT, 61, SpecificRemapper.ENTITY),
	EGG(EType.OBJECT, 62, SpecificRemapper.ENTITY),
	FIREBALL(EType.OBJECT, 63, SpecificRemapper.ENTITY),
	FIRECHARGE(EType.OBJECT, 64, SpecificRemapper.ENTITY),
	ENDERPEARL(EType.OBJECT, 65, SpecificRemapper.ENTITY),
	WITHER_SKULL(EType.OBJECT, 66, SpecificRemapper.FIREBALL,
		//is charged
		new Mapping(6)
		.addRemap(5, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(10, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	FALLING_OBJECT(EType.OBJECT, 70, SpecificRemapper.ENTITY),
	ENDEREYE(EType.OBJECT, 72, SpecificRemapper.ENTITY),
	POTION(EType.OBJECT, 73, SpecificRemapper.ENTITY),
	DRAGON_EGG(EType.OBJECT, 74, SpecificRemapper.ENTITY),
	EXP_BOTTLE(EType.OBJECT, 75, SpecificRemapper.ENTITY),
	LEASH_KNOT(EType.OBJECT, 77, SpecificRemapper.ENTITY),
	FISHING_FLOAT(EType.OBJECT, 90, SpecificRemapper.ENTITY),
	ITEM(EType.OBJECT, 2, SpecificRemapper.ENTITY,
		//item
		new Mapping(6)
		.addRemap(5, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(10, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART(EType.OBJECT, 10, SpecificRemapper.ENTITY,
		//shaking power
		new Mapping(6)
		.addRemap(5, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//shaking direction
		new Mapping(7)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//block y
		new Mapping(10)
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(21, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//show block
		new Mapping(11)
		.addRemap(10, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(22, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//damage taken
		new Mapping(8)
		.addRemap(7, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(19, ValueRemapperNoOp.FLOAT, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1))
		.addRemap(19, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//block
		new Mapping(9)
		.addRemap(8, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(20, ValueRemapperNumberToInt.INSTANCE, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(20, new ValueRemapper<DataWatcherObjectVarInt>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectVarInt object) {
				int value = object.getValue();
				int id = value & 0xFFFF;
				int data = value >> 12;
				return new DataWatcherObjectInt((data << 16) | id);
			}
		}, ProtocolVersionsHelper.BEFORE_1_6)
	),
	ARROW(EType.OBJECT, 60, SpecificRemapper.ENTITY,
		//is critical
		new Mapping(6)
		.addRemap(5, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(15, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPECTRAL_ARROW(EType.OBJECT, 91, SpecificRemapper.ARROW),
	TIPPED_ARROW(EType.OBJECT, 92, SpecificRemapper.ARROW),
	FIREWORK(EType.OBJECT, 76, SpecificRemapper.ENTITY,
		//info
		new Mapping(6)
		.addRemap(5, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(8, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.BEFORE_1_9)
	),
	ITEM_FRAME(EType.OBJECT, 71, SpecificRemapper.ENTITY,
		//item
		new Mapping(6)
		.addRemap(5, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(8, ValueRemapperNoOp.ITEMSTACK, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(2, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.BEFORE_1_8),
		//rotation
		new Mapping(7)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(9, ValueRemapperNumberToByte.INSTANCE, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(3, new ValueRemapper<DataWatcherObjectVarInt>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (object.getValue() >> 1));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8)
	),
	ENDER_CRYSTAL(EType.OBJECT, 51, SpecificRemapper.ENTITY),
	ARMOR_STAND_OBJECT(EType.OBJECT, 78, SpecificRemapper.ARMOR_STAND),
	AREA_EFFECT_CLOUD(EType.OBJECT, 3, SpecificRemapper.ENTITY),
	SHULKER_BULLET(EType.OBJECT, 67, SpecificRemapper.ENTITY),
	DRAGON_FIREBALL(EType.OBJECT, 93, SpecificRemapper.ENTITY);


	private static final SpecificRemapper[] OBJECT_BY_TYPE_ID = new SpecificRemapper[256];
	private static final SpecificRemapper[] MOB_BY_TYPE_ID = new SpecificRemapper[256];

	static {
		Arrays.fill(OBJECT_BY_TYPE_ID, SpecificRemapper.NONE);
		Arrays.fill(MOB_BY_TYPE_ID, SpecificRemapper.NONE);
		for (SpecificRemapper type : values()) {
			switch (type.etype) {
				case OBJECT: {
					OBJECT_BY_TYPE_ID[type.typeId] = type;
					break;
				}
				case MOB: {
					MOB_BY_TYPE_ID[type.typeId] = type;
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	public static SpecificRemapper getObjectByTypeId(int objectTypeId) {
		return OBJECT_BY_TYPE_ID[objectTypeId];
	}

	public static SpecificRemapper getMobByTypeId(int mobTypeId) {
		return MOB_BY_TYPE_ID[mobTypeId];
	}

	private final EType etype;
	private final int typeId;
	private final EnumMap<ProtocolVersion, ArrayList<MappingEntry>> entries = new EnumMap<ProtocolVersion, ArrayList<MappingEntry>>(ProtocolVersion.class);
	{
		for (ProtocolVersion version : ProtocolVersion.values()) {
			entries.put(version, new ArrayList<MappingEntry>());
		}
	}

	@SuppressWarnings("deprecation")
	SpecificRemapper(EType etype, EntityType type, Mapping... entries) {
		this(etype, type.getTypeId(), entries);
	}

	SpecificRemapper(EType etype, int typeId, Mapping... entries) {
		for (Mapping mapping : entries) {
			for (Mapping.Entry entry : mapping.entries) {
				for (ProtocolVersion version : entry.versions) {
					this.entries.get(version).add(new MappingEntry(mapping.idFrom, entry.idTo, entry.vremap));
				}
			}
		}
		this.etype = etype;
		this.typeId = typeId;
	}

	@SuppressWarnings("deprecation")
	SpecificRemapper(EType etype, EntityType type, SpecificRemapper superType, Mapping... entries) {
		this(etype, type.getTypeId(), superType, entries);
	}

	SpecificRemapper(EType etype, int typeId, SpecificRemapper superType, Mapping... entries) {
		this(etype, typeId, entries);
		for (Entry<ProtocolVersion, ArrayList<MappingEntry>> entry : superType.entries.entrySet()) {
			this.entries.get(entry.getKey()).addAll(entry.getValue());
		}
	}

	public List<MappingEntry> getRemaps(ProtocolVersion version) {
		return entries.get(version);
	}

	private enum EType {
		NONE, OBJECT, MOB
	}

	private static class Mapping {
		private int idFrom;
		private final List<Entry> entries = new ArrayList<Entry>();
		public Mapping(int idFrom) {
			this.idFrom = idFrom;
		}
		public Mapping addRemap(int to, ValueRemapper<?> valueremap, ProtocolVersion... versions) {
			entries.add(new Entry(to, valueremap, versions));
			return this;
		}
		private static class Entry {
			private int idTo;
			private ValueRemapper<?> vremap;
			private ProtocolVersion[] versions;
			public Entry(int to, ValueRemapper<?> vremap, ProtocolVersion[] versions) {
				this.idTo = to;
				this.vremap = vremap;
				this.versions = versions;
			}
		}
	}

	public static void init() {
	}

}
