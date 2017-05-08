package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperBooleanToByte;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperNumberToByte;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperNumberToInt;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperNumberToShort;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperStringClamp;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBlockState;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShort;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.ProtocolVersionsHelper;

public enum SpecificRemapper {

	//TODO: add new entities entries, add type adder for horse, skeleton, minecart
	NONE(NetworkEntityType.NONE),
	ENTITY(NetworkEntityType.ENTITY,
		//flags
		new Mapping(0)
		.addRemap(0, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL),
		//air
		new Mapping(1)
		.addRemap(1, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_9__1_12)
		.addRemap(1, ValueRemapperNumberToShort.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//nametag
		new Mapping(2)
		.addRemap(2, ValueRemapperNoOp.STRING, ProtocolVersionsHelper.RANGE__1_9__1_12),
		//nametag visible
		new Mapping(3)
		.addRemap(3, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_9__1_12),
		//silent
		new Mapping(4)
		.addRemap(4, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_9__1_12),
		//no gravity
		new Mapping(5)
		.addRemap(5, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
	),
	LIVING(NetworkEntityType.LIVING, SpecificRemapper.ENTITY,
		//nametag
		new Mapping(2)
		.addRemap(2, ValueRemapperNoOp.STRING, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(10, new ValueRemapperStringClamp(64), ProtocolVersionsHelper.RANGE__1_6__1_7)
		.addRemap(5, new ValueRemapperStringClamp(64), ProtocolVersionsHelper.BEFORE_1_6),
		//nametag visible
		new Mapping(3)
		.addRemap(3, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(11, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_7)
		.addRemap(6, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//hand use
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(5, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9),
		//health
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(6, ValueRemapperNoOp.FLOAT, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_6_1)),
		//pcolor
		new Mapping(8)
		.addRemap(8, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(7, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_7)
		.addRemap(8, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//pambient
		new Mapping(9)
		.addRemap(9, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(8, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(8, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_7)
		.addRemap(9, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//arrowsn
		new Mapping(10)
		.addRemap(10, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(9, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_7)
		.addRemap(10, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6)
	),
	INSENTIENT(NetworkEntityType.INSENTIENT, SpecificRemapper.LIVING,
		//noai
		new Mapping(11)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(10, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(15, ValueRemapperNoOp.BYTE, ProtocolVersion.MINECRAFT_1_8)
	),
	PLAYER(NetworkEntityType.PLAYER, SpecificRemapper.LIVING,
		//additional hearts
		new Mapping(11)
		.addRemap(11, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(10, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.BEFORE_1_9),
		//score
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//skin flags(cape enabled for some protocols)
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(10, ValueRemapperNoOp.BYTE, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1))
	),
	AGEABLE(NetworkEntityType.AGEABLE, SpecificRemapper.INSENTIENT,
		//age
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
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
		.addRemap(12, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_7)
	),
	TAMEABLE(NetworkEntityType.TAMEABLE, SpecificRemapper.AGEABLE,
		//tame flags
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	ARMOR_STAND(NetworkEntityType.ARMOR_STAND, SpecificRemapper.LIVING,
		//parts position
		new Mapping(11)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(10, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Mapping(14)
		.addRemap(14, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(13, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(14, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Mapping(16)
		.addRemap(16, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(15, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Mapping(17)
		.addRemap(17, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(16, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_8__1_9)
	),
	COW(NetworkEntityType.COW, SpecificRemapper.AGEABLE),
	MUSHROOM_COW(NetworkEntityType.MUSHROOM_COW, SpecificRemapper.COW),
	CHICKEN(NetworkEntityType.CHICKEN, SpecificRemapper.AGEABLE),
	SQUID(NetworkEntityType.SQUID, SpecificRemapper.INSENTIENT),
	BASE_HORSE(NetworkEntityType.BASE_HORSE, SpecificRemapper.AGEABLE,
		//info flags
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	BATTLE_HORSE(NetworkEntityType.BATTLE_HORSE, SpecificRemapper.BASE_HORSE,
		//color/variant
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(14, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(20, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//armor
		new Mapping(16)
		.addRemap(16, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_11__1_12)
		.addRemap(17, ValueRemapperNoOp.VARINT, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(16, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(22, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	CARGO_HORSE(NetworkEntityType.CARGO_HORSE, SpecificRemapper.BASE_HORSE,
		//has chest
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_11__1_12)
	),
	COMMON_HORSE(NetworkEntityType.COMMON_HORSE, SpecificRemapper.BATTLE_HORSE),
	ZOMBIE_HORSE(NetworkEntityType.ZOMBIE_HORSE, SpecificRemapper.BATTLE_HORSE),
	SKELETON_HORSE(NetworkEntityType.SKELETON_HORSE, SpecificRemapper.BATTLE_HORSE),
	DONKEY(NetworkEntityType.DONKEY, SpecificRemapper.CARGO_HORSE),
	MULE(NetworkEntityType.MULE, SpecificRemapper.CARGO_HORSE),
	LAMA(NetworkEntityType.LAMA, SpecificRemapper.CARGO_HORSE,
		//strength
		new Mapping(16)
		.addRemap(16, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_11__1_12),
		//carpet color
		new Mapping(17)
		.addRemap(17, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_11__1_12),
		//type
		new Mapping(18)
		.addRemap(18, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_11__1_12)
	),
	BAT(NetworkEntityType.BAT, SpecificRemapper.INSENTIENT,
		//hanging
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	OCELOT(NetworkEntityType.OCELOT, SpecificRemapper.TAMEABLE,
		//type
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(14, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	WOLF(NetworkEntityType.WOLF, SpecificRemapper.TAMEABLE,
		//health
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(14, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNoOp.FLOAT, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1))
		.addRemap(18, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//begging
		new Mapping(16)
		.addRemap(16, ValueRemapperNoOp.BOOLEAN, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(15, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(19, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//collar color
		new Mapping(17)
		.addRemap(17, ValueRemapperNoOp.VARINT, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(16, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(20, ValueRemapperNumberToByte.INSTANCE, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(20, new ValueRemapper<DataWatcherObjectVarInt>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (15 - object.getValue()));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8)
	),
	PIG(NetworkEntityType.PIG, SpecificRemapper.AGEABLE,
		//has saddle
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//boost time
		new Mapping(14)
		.addRemap(14, ValueRemapperNoOp.VARINT, ProtocolVersion.MINECRAFT_1_11_1)
	),
	RABBIT(NetworkEntityType.RABBIT, SpecificRemapper.AGEABLE,
		//type
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHEEP(NetworkEntityType.SHEEP, SpecificRemapper.AGEABLE,
		//info flags (color + sheared)
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	POLAR_BEAR(NetworkEntityType.POLAR_BEAR, SpecificRemapper.AGEABLE,
		//standing up
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
	),
	VILLAGER(NetworkEntityType.VILLAGER, SpecificRemapper.AGEABLE,
		//profession
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	ENDERMAN(NetworkEntityType.ENDERMAN, SpecificRemapper.INSENTIENT,
		//carried block
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BLOCKSTATE, ProtocolVersionsHelper.RANGE__1_10__1_12)
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
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	GIANT(NetworkEntityType.GIANT, SpecificRemapper.INSENTIENT),
	SILVERFISH(NetworkEntityType.SILVERFISH, SpecificRemapper.INSENTIENT),
	ENDERMITE(NetworkEntityType.ENDERMITE, SpecificRemapper.INSENTIENT),
	ENDER_DRAGON(NetworkEntityType.ENDER_DRAGON, SpecificRemapper.INSENTIENT,
		//phase
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
	),
	SNOWMAN(NetworkEntityType.SNOWMAN, SpecificRemapper.INSENTIENT,
		//no hat
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
	),
	ZOMBIE(NetworkEntityType.ZOMBIE, SpecificRemapper.INSENTIENT,
		//is baby
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(12, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//profession
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(13, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//hands up
		new Mapping(14)
		.addRemap(14, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_11__1_12)
		.addRemap(15, ValueRemapperNoOp.BOOLEAN, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(14, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
	),
	ZOMBIE_VILLAGER(NetworkEntityType.ZOMBIE_VILLAGER, SpecificRemapper.ZOMBIE,
		//is converting
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_11__1_12)
		.addRemap(14, ValueRemapperNoOp.BOOLEAN, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(14, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	HUSK(NetworkEntityType.HUSK, SpecificRemapper.ZOMBIE),
	ZOMBIE_PIGMAN(NetworkEntityType.ZOMBIE_PIGMAN, SpecificRemapper.ZOMBIE),
	BLAZE(NetworkEntityType.BLAZE, SpecificRemapper.INSENTIENT,
		//on fire
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPIDER(NetworkEntityType.SPIDER, SpecificRemapper.LIVING,
		//is climbing
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	CAVE_SPIDER(NetworkEntityType.CAVE_SPIDER, SpecificRemapper.SPIDER),
	CREEPER(NetworkEntityType.CREEPER, SpecificRemapper.INSENTIENT,
		//state
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//is powered
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//ignited
		new Mapping(14)
		.addRemap(14, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	GHAST(NetworkEntityType.GHAST, SpecificRemapper.INSENTIENT,
		//is attacking
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SLIME(NetworkEntityType.SLIME, SpecificRemapper.INSENTIENT,
		//size
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	MAGMA_CUBE(NetworkEntityType.MAGMA_CUBE, SpecificRemapper.SLIME),
	BASE_SKELETON(NetworkEntityType.BASE_SKELETON, SpecificRemapper.INSENTIENT,
		//is attacking
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_11__1_12)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
	),
	SKELETON(NetworkEntityType.SKELETON, SpecificRemapper.BASE_SKELETON),
	WITHER_SKELETON(NetworkEntityType.WITHER_SKELETON, SpecificRemapper.BASE_SKELETON),
	STRAY(NetworkEntityType.STRAY, SpecificRemapper.BASE_SKELETON),
	WITCH(NetworkEntityType.WITCH, SpecificRemapper.INSENTIENT,
		//agressive
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	IRON_GOLEM(NetworkEntityType.IRON_GOLEM, SpecificRemapper.INSENTIENT,
		//player created
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHULKER(NetworkEntityType.SHULKER, SpecificRemapper.INSENTIENT,
		//direction
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.DIRECTION, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.DIRECTION, ProtocolVersionsHelper.ALL_1_9),
		//attachment pos
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.OPTIONAL_POSITION, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.OPTIONAL_POSITION, ProtocolVersionsHelper.ALL_1_9),
		//shield h
		new Mapping(14)
		.addRemap(14, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(13, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9),
		//color
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_11__1_12)
	),
	WITHER(NetworkEntityType.WITHER, SpecificRemapper.INSENTIENT,
		//target 1
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//target 2
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//target 3
		new Mapping(14)
		.addRemap(14, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(19, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//invulnerable time
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(14, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(20, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	GUARDIAN(NetworkEntityType.GUARDIAN, SpecificRemapper.INSENTIENT,
		//spikes
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_11__1_12)
		.addRemap(12, new ValueRemapper<DataWatcherObjectBoolean>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(11, new ValueRemapper<DataWatcherObjectBoolean>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, new ValueRemapper<DataWatcherObjectBoolean>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectInt(object.getValue() ? (byte) 2 : (byte) 0);
			}
		}, ProtocolVersion.MINECRAFT_1_8),
		//target id
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersion.MINECRAFT_1_8)
	),
	ELDER_GUARDIAN(NetworkEntityType.ELDER_GUARDIAN, SpecificRemapper.GUARDIAN),
	VINDICATOR(NetworkEntityType.VINDICATOR, SpecificRemapper.INSENTIENT,
		//agressive
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_11__1_12)
	),
	EVOKER(NetworkEntityType.EVOKER, SpecificRemapper.INSENTIENT,
		//spell
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_11__1_12)
	),
	VEX(NetworkEntityType.VEX, SpecificRemapper.INSENTIENT,
		//vex
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_11__1_12)
	),
	ARMOR_STAND_MOB(NetworkEntityType.ARMOR_STAND_MOB, SpecificRemapper.ARMOR_STAND),
	BOAT(NetworkEntityType.BOAT,
		//time since hit
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(5, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//forward direction
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//damage taken
		new Mapping(8)
		.addRemap(8, ValueRemapperNoOp.FLOAT, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(7, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(19, ValueRemapperNoOp.FLOAT, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1))
		.addRemap(19, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//type
		new Mapping(9)
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12),
		//left paddle
		new Mapping(10)
		.addRemap(10, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12),
		//right paddle
		new Mapping(11)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
	),
	TNT(NetworkEntityType.TNT, SpecificRemapper.ENTITY,
		//fuse ticks
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(5, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
	),
	SNOWBALL(NetworkEntityType.SNOWBALL, SpecificRemapper.ENTITY),
	EGG(NetworkEntityType.EGG, SpecificRemapper.ENTITY),
	FIREBALL(NetworkEntityType.FIREBALL, SpecificRemapper.ENTITY),
	FIRECHARGE(NetworkEntityType.FIRECHARGE, SpecificRemapper.ENTITY),
	ENDERPEARL(NetworkEntityType.ENDERPEARL, SpecificRemapper.ENTITY),
	WITHER_SKULL(NetworkEntityType.WITHER_SKULL, SpecificRemapper.FIREBALL,
		//is charged
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(5, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(10, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	FALLING_OBJECT(NetworkEntityType.FALLING_OBJECT, SpecificRemapper.ENTITY),
	ENDEREYE(NetworkEntityType.ENDEREYE, SpecificRemapper.ENTITY),
	POTION(NetworkEntityType.POTION, SpecificRemapper.ENTITY,
		//potion item (remap to 2 ids for 1.10.*, because 1.10.2 uses id 6, and 1.10 uses id 7)
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(7, ValueRemapperNoOp.ITEMSTACK, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(6, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.ALL_1_9)
	),
	EXP_BOTTLE(NetworkEntityType.EXP_BOTTLE, SpecificRemapper.ENTITY),
	LEASH_KNOT(NetworkEntityType.LEASH_KNOT, SpecificRemapper.ENTITY),
	FISHING_FLOAT(NetworkEntityType.FISHING_FLOAT, SpecificRemapper.ENTITY,
		//hooked entity id
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(5, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
	),
	ITEM(NetworkEntityType.ITEM, SpecificRemapper.ENTITY,
		//item
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(5, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(10, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART(NetworkEntityType.MINECART, SpecificRemapper.ENTITY,
		//shaking power
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(5, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//shaking direction
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//block y
		new Mapping(10)
		.addRemap(10, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(21, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//show block
		new Mapping(11)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(10, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(22, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//damage taken
		new Mapping(8)
		.addRemap(8, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(7, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(19, ValueRemapperNoOp.FLOAT, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1))
		.addRemap(19, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//block
		new Mapping(9)
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
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
		}, ProtocolVersionsHelper.BEFORE_1_6),
		//powered or command based on what object data it had
		new Mapping(12)
		.addRemap(12, new ValueRemapper<DataWatcherObject<?>>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObject<?> object) {
				return object;
			}
		}, ProtocolVersionsHelper.RANGE__1_11__1_12),
		//last output
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.STRING, ProtocolVersionsHelper.RANGE__1_11__1_12)
	),
	ARROW(NetworkEntityType.ARROW, SpecificRemapper.ENTITY,
		//is critical
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(5, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(15, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPECTRAL_ARROW(NetworkEntityType.SPECTRAL_ARROW, SpecificRemapper.ARROW),
	TIPPED_ARROW(NetworkEntityType.TIPPED_ARROW, SpecificRemapper.ARROW,
		//color
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
	),
	FIREWORK(NetworkEntityType.FIREWORK, SpecificRemapper.ENTITY,
		//info
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(5, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(8, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.BEFORE_1_9),
		//who used
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersion.MINECRAFT_1_11_1)
	),
	ITEM_FRAME(NetworkEntityType.ITEM_FRAME, SpecificRemapper.ENTITY,
		//item
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(5, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(8, ValueRemapperNoOp.ITEMSTACK, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(2, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.BEFORE_1_8),
		//rotation
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(9, ValueRemapperNumberToByte.INSTANCE, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(3, new ValueRemapper<DataWatcherObjectVarInt>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (object.getValue() >> 1));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8)
	),
	ENDER_CRYSTAL(NetworkEntityType.ENDER_CRYSTAL, SpecificRemapper.ENTITY,
		//target
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.OPTIONAL_POSITION, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(5, ValueRemapperNoOp.OPTIONAL_POSITION, ProtocolVersionsHelper.ALL_1_9),
		//show botton
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(6, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
	),
	ARMOR_STAND_OBJECT(NetworkEntityType.ARMOR_STAND_OBJECT, SpecificRemapper.ARMOR_STAND),
	AREA_EFFECT_CLOUD(NetworkEntityType.AREA_EFFECT_CLOUD, SpecificRemapper.ENTITY,
		//radius
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(5, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.ALL_1_9),
		//color
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9),
		//single point
		new Mapping(8)
		.addRemap(8, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(7, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9),
		//particle id
		new Mapping(9)
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(8, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9),
		//particle param 1
		new Mapping(10)
		.addRemap(10, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9),
		//particle param 2
		new Mapping(11)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_12)
		.addRemap(10, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
	),
	SHULKER_BULLET(NetworkEntityType.SHULKER_BULLET, SpecificRemapper.ENTITY),
	DRAGON_FIREBALL(NetworkEntityType.DRAGON_FIREBALL, SpecificRemapper.ENTITY),
	EVOCATOR_FANGS(NetworkEntityType.EVOCATOR_FANGS, SpecificRemapper.ENTITY);

	private static final EnumMap<NetworkEntityType, SpecificRemapper> wtype = new EnumMap<>(NetworkEntityType.class);

	static {
		for (SpecificRemapper remapper : SpecificRemapper.values()) {
			wtype.put(remapper.type, remapper);
		}
	}

	public static SpecificRemapper fromWatchedType(NetworkEntityType type) {
		return wtype.getOrDefault(type, SpecificRemapper.NONE);
	}

	private final NetworkEntityType type;
	private final EnumMap<ProtocolVersion, ArrayList<MappingEntry>> entries = new EnumMap<>(ProtocolVersion.class);
	{
		for (ProtocolVersion version : ProtocolVersion.values()) {
			entries.put(version, new ArrayList<MappingEntry>());
		}
	}

	SpecificRemapper(NetworkEntityType type, Mapping... entries) {
		this.type = type;
		for (Mapping mapping : entries) {
			for (Mapping.Entry entry : mapping.entries) {
				for (ProtocolVersion version : entry.versions) {
					this.entries.get(version).add(new MappingEntry(mapping.idFrom, entry.idTo, entry.vremap));
				}
			}
		}
	}

	SpecificRemapper(NetworkEntityType type, SpecificRemapper superType, Mapping... entries) {
		this(type, entries);
		for (Entry<ProtocolVersion, ArrayList<MappingEntry>> entry : superType.entries.entrySet()) {
			this.entries.get(entry.getKey()).addAll(entry.getValue());
		}
	}

	public List<MappingEntry> getRemaps(ProtocolVersion version) {
		return entries.get(version);
	}

	private static class Mapping {
		private final int idFrom;
		private final List<Entry> entries = new ArrayList<>();
		public Mapping(int idFrom) {
			this.idFrom = idFrom;
		}
		public Mapping addRemap(int to, ValueRemapper<?> valueremap, ProtocolVersion... versions) {
			entries.add(new Entry(to, valueremap, versions));
			return this;
		}
		private static class Entry {
			private final int idTo;
			private final ValueRemapper<?> vremap;
			private final ProtocolVersion[] versions;
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
