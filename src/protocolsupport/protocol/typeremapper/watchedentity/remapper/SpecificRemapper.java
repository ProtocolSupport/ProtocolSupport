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
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedType;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBlockState;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShort;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.utils.ProtocolVersionsHelper;

public enum SpecificRemapper {

	//TODO: add new entities entries, add type adder for horse, skeleton, minecart
	NONE(WatchedType.NONE),
	ENTITY(WatchedType.ENTITY,
		//flags
		new Mapping(0)
		.addRemap(0, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL),
		//air
		new Mapping(1)
		.addRemap(1, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_9__1_11)
		.addRemap(1, ValueRemapperNumberToShort.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//nametag
		new Mapping(2)
		.addRemap(2, ValueRemapperNoOp.STRING, ProtocolVersionsHelper.RANGE__1_9__1_11),
		//nametag visible
		new Mapping(3)
		.addRemap(3, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_9__1_11),
		//silent
		new Mapping(4)
		.addRemap(4, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_9__1_11),
		//no gravity
		new Mapping(5)
		.addRemap(5, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
	),
	LIVING(WatchedType.LIVING, SpecificRemapper.ENTITY,
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
		.addRemap(6, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(5, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9),
		//health
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(6, ValueRemapperNoOp.FLOAT, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9_4, ProtocolVersion.MINECRAFT_1_6_1)),
		//pcolor
		new Mapping(8)
		.addRemap(8, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(7, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_7)
		.addRemap(8, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//pambient
		new Mapping(9)
		.addRemap(9, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(8, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(8, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_7)
		.addRemap(9, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//arrowsn
		new Mapping(10)
		.addRemap(10, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(9, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.RANGE__1_6__1_7)
		.addRemap(10, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6)
	),
	INSENTIENT(WatchedType.INSENTIENT, SpecificRemapper.LIVING,
		//noai
		new Mapping(11)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(10, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(15, ValueRemapperNoOp.BYTE, ProtocolVersion.MINECRAFT_1_8)
	),
	PLAYER(WatchedType.PLAYER, SpecificRemapper.LIVING,
		//additional hearts
		new Mapping(11)
		.addRemap(11, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(10, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.BEFORE_1_9),
		//score
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//skin flags(cape enabled for some protocols)
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(10, ValueRemapperNoOp.BYTE, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1))
	),
	AGEABLE(WatchedType.AGEABLE, SpecificRemapper.INSENTIENT,
		//age
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
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
	TAMEABLE(WatchedType.TAMEABLE, SpecificRemapper.AGEABLE,
		//tame flags
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	ARMOR_STAND(WatchedType.ARMOR_STAND, SpecificRemapper.LIVING,
		//parts position
		new Mapping(11)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(10, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Mapping(14)
		.addRemap(14, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(13, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(14, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Mapping(16)
		.addRemap(16, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(15, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_8__1_9),
		new Mapping(17)
		.addRemap(17, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(16, ValueRemapperNoOp.VECTOR3F, ProtocolVersionsHelper.RANGE__1_8__1_9)
	),
	COW(WatchedType.COW, SpecificRemapper.AGEABLE),
	MUSHROOM_COW(WatchedType.MUSHROOM_COW, SpecificRemapper.COW),
	CHICKEN(WatchedType.CHICKEN, SpecificRemapper.AGEABLE),
	SQUID(WatchedType.SQUID, SpecificRemapper.INSENTIENT),
	BASE_HORSE(WatchedType.BASE_HORSE, SpecificRemapper.AGEABLE,
		//info flags
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	BATTLE_HORSE(WatchedType.BATTLE_HORSE, SpecificRemapper.BASE_HORSE,
		//color/variant
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(14, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(20, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//armor
		new Mapping(16)
		.addRemap(16, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_11)
		.addRemap(17, ValueRemapperNoOp.VARINT, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(16, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(22, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	CARGO_HORSE(WatchedType.CARGO_HORSE, SpecificRemapper.BASE_HORSE,
		//has chest
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_11)
	),
	COMMON_HORSE(WatchedType.COMMON_HORSE, SpecificRemapper.BATTLE_HORSE),
	ZOMBIE_HORSE(WatchedType.ZOMBIE_HORSE, SpecificRemapper.BATTLE_HORSE),
	SKELETON_HORSE(WatchedType.SKELETON_HORSE, SpecificRemapper.BATTLE_HORSE),
	DONKEY(WatchedType.DONKEY, SpecificRemapper.CARGO_HORSE),
	MULE(WatchedType.MULE, SpecificRemapper.CARGO_HORSE),
	LAMA(WatchedType.LAMA, SpecificRemapper.CARGO_HORSE,
		//strength
		new Mapping(16)
		.addRemap(16, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_11),
		//carpet color
		new Mapping(17)
		.addRemap(17, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_11),
		//type
		new Mapping(18)
		.addRemap(18, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_11)
	),
	BAT(WatchedType.BAT, SpecificRemapper.INSENTIENT,
		//hanging
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	OCELOT(WatchedType.OCELOT, SpecificRemapper.TAMEABLE,
		//type
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(14, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	WOLF(WatchedType.WOLF, SpecificRemapper.TAMEABLE,
		//health
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE__1_10__1_11)
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
	PIG(WatchedType.PIG, SpecificRemapper.AGEABLE,
		//has saddle
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//boost time
		new Mapping(14)
		.addRemap(14, ValueRemapperNoOp.VARINT, ProtocolVersion.MINECRAFT_1_11_1)
	),
	RABBIT(WatchedType.RABBIT, SpecificRemapper.AGEABLE,
		//type
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHEEP(WatchedType.SHEEP, SpecificRemapper.AGEABLE,
		//info flags (color + sheared)
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	POLAR_BEAR(WatchedType.POLAR_BEAR, SpecificRemapper.AGEABLE,
		//standing up
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
	),
	VILLAGER(WatchedType.VILLAGER, SpecificRemapper.AGEABLE,
		//profession
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	ENDERMAN(WatchedType.ENDERMAN, SpecificRemapper.INSENTIENT,
		//carried block
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BLOCKSTATE, ProtocolVersionsHelper.RANGE__1_10__1_11)
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
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	GIANT(WatchedType.GIANT, SpecificRemapper.INSENTIENT),
	SILVERFISH(WatchedType.SILVERFISH, SpecificRemapper.INSENTIENT),
	ENDERMITE(WatchedType.ENDERMITE, SpecificRemapper.INSENTIENT),
	ENDER_DRAGON(WatchedType.ENDER_DRAGON, SpecificRemapper.INSENTIENT,
		//phase
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
	),
	SNOWMAN(WatchedType.SNOWMAN, SpecificRemapper.INSENTIENT,
		//no hat
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
	),
	ZOMBIE(WatchedType.ZOMBIE, SpecificRemapper.INSENTIENT,
		//is baby
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(12, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//profession
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(13, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//hands up
		new Mapping(14)
		.addRemap(14, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_11)
		.addRemap(15, ValueRemapperNoOp.BOOLEAN, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(14, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
	),
	ZOMBIE_VILLAGER(WatchedType.ZOMBIE_VILLAGER, SpecificRemapper.ZOMBIE,
		//is converting
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_11)
		.addRemap(14, ValueRemapperNoOp.BOOLEAN, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(14, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	HUSK(WatchedType.HUSK, SpecificRemapper.ZOMBIE),
	ZOMBIE_PIGMAN(WatchedType.ZOMBIE_PIGMAN, SpecificRemapper.ZOMBIE),
	BLAZE(WatchedType.BLAZE, SpecificRemapper.INSENTIENT,
		//on fire
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPIDER(WatchedType.SPIDER, SpecificRemapper.LIVING,
		//is climbing
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	CAVE_SPIDER(WatchedType.CAVE_SPIDER, SpecificRemapper.SPIDER),
	CREEPER(WatchedType.CREEPER, SpecificRemapper.INSENTIENT,
		//state
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//is powered
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//ignited
		new Mapping(14)
		.addRemap(14, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	GHAST(WatchedType.GHAST, SpecificRemapper.INSENTIENT,
		//is attacking
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SLIME(WatchedType.SLIME, SpecificRemapper.INSENTIENT,
		//size
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNumberToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	MAGMA_CUBE(WatchedType.MAGMA_CUBE, SpecificRemapper.SLIME),
	BASE_SKELETON(WatchedType.BASE_SKELETON, SpecificRemapper.INSENTIENT,
		//is attacking
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_11)
		.addRemap(13, ValueRemapperNoOp.BOOLEAN, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
	),
	SKELETON(WatchedType.SKELETON, SpecificRemapper.BASE_SKELETON),
	WITHER_SKELETON(WatchedType.WITHER_SKELETON, SpecificRemapper.BASE_SKELETON),
	STRAY(WatchedType.STRAY, SpecificRemapper.BASE_SKELETON),
	WITCH(WatchedType.WITCH, SpecificRemapper.INSENTIENT,
		//agressive
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	IRON_GOLEM(WatchedType.IRON_GOLEM, SpecificRemapper.INSENTIENT,
		//player created
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(16, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHULKER(WatchedType.SHULKER, SpecificRemapper.INSENTIENT,
		//direction
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.DIRECTION, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.DIRECTION, ProtocolVersionsHelper.ALL_1_9),
		//attachment pos
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.OPTIONAL_POSITION, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.OPTIONAL_POSITION, ProtocolVersionsHelper.ALL_1_9),
		//shield h
		new Mapping(14)
		.addRemap(14, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(13, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9),
		//color
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_11)
	),
	WITHER(WatchedType.WITHER, SpecificRemapper.INSENTIENT,
		//target 1
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//target 2
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//target 3
		new Mapping(14)
		.addRemap(14, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(19, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//invulnerable time
		new Mapping(15)
		.addRemap(15, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(14, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(20, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	GUARDIAN(WatchedType.GUARDIAN, SpecificRemapper.INSENTIENT,
		//spikes
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_11)
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
		.addRemap(13, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(12, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersion.MINECRAFT_1_8)
	),
	ELDER_GUARDIAN(WatchedType.ELDER_GUARDIAN, SpecificRemapper.GUARDIAN),
	VINDICATOR(WatchedType.VINDICATOR, SpecificRemapper.INSENTIENT,
		//agressive
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_11)
	),
	EVOKER(WatchedType.EVOKER, SpecificRemapper.INSENTIENT,
		//spell
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_11)
	),
	VEX(WatchedType.VEX, SpecificRemapper.INSENTIENT,
		//vex
		new Mapping(12)
		.addRemap(12, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_11)
	),
	ARMOR_STAND_MOB(WatchedType.ARMOR_STAND_MOB, SpecificRemapper.ARMOR_STAND),
	BOAT(WatchedType.BOAT,
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
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11),
		//left paddle
		new Mapping(10)
		.addRemap(10, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11),
		//right paddle
		new Mapping(11)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
	),
	TNT(WatchedType.TNT, SpecificRemapper.ENTITY,
		//fuse ticks
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(5, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
	),
	SNOWBALL(WatchedType.SNOWBALL, SpecificRemapper.ENTITY),
	EGG(WatchedType.EGG, SpecificRemapper.ENTITY),
	FIREBALL(WatchedType.FIREBALL, SpecificRemapper.ENTITY),
	FIRECHARGE(WatchedType.FIRECHARGE, SpecificRemapper.ENTITY),
	ENDERPEARL(WatchedType.ENDERPEARL, SpecificRemapper.ENTITY),
	WITHER_SKULL(WatchedType.WITHER_SKULL, SpecificRemapper.FIREBALL,
		//is charged
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(5, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(10, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	FALLING_OBJECT(WatchedType.FALLING_OBJECT, SpecificRemapper.ENTITY),
	ENDEREYE(WatchedType.ENDEREYE, SpecificRemapper.ENTITY),
	POTION(WatchedType.POTION, SpecificRemapper.ENTITY,
		//potion item (remap to 2 ids for 1.10.*, because 1.10.2 uses id 6, and 1.10 uses id 7)
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(7, ValueRemapperNoOp.ITEMSTACK, ProtocolVersion.MINECRAFT_1_10)
		.addRemap(6, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.ALL_1_9)
	),
	EXP_BOTTLE(WatchedType.EXP_BOTTLE, SpecificRemapper.ENTITY),
	LEASH_KNOT(WatchedType.LEASH_KNOT, SpecificRemapper.ENTITY),
	FISHING_FLOAT(WatchedType.FISHING_FLOAT, SpecificRemapper.ENTITY,
		//hooked entity id
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(5, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
	),
	ITEM(WatchedType.ITEM, SpecificRemapper.ENTITY,
		//item
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(5, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(10, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART(WatchedType.MINECART, SpecificRemapper.ENTITY,
		//shaking power
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(5, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(17, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//shaking direction
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(18, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//block y
		new Mapping(10)
		.addRemap(10, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(21, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//show block
		new Mapping(11)
		.addRemap(11, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(10, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(22, ValueRemapperBooleanToByte.INSTANCE, ProtocolVersionsHelper.BEFORE_1_9),
		//damage taken
		new Mapping(8)
		.addRemap(8, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(7, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(19, ValueRemapperNoOp.FLOAT, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1))
		.addRemap(19, ValueRemapperNumberToInt.INSTANCE, ProtocolVersionsHelper.BEFORE_1_6),
		//block
		new Mapping(9)
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
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
		}, ProtocolVersionsHelper.ALL_1_11),
		//last output
		new Mapping(13)
		.addRemap(13, ValueRemapperNoOp.STRING, ProtocolVersionsHelper.ALL_1_11)
	),
	ARROW(WatchedType.ARROW, SpecificRemapper.ENTITY,
		//is critical
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(5, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(15, ValueRemapperNoOp.BYTE, ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPECTRAL_ARROW(WatchedType.SPECTRAL_ARROW, SpecificRemapper.ARROW),
	TIPPED_ARROW(WatchedType.TIPPED_ARROW, SpecificRemapper.ARROW,
		//color
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
	),
	FIREWORK(WatchedType.FIREWORK, SpecificRemapper.ENTITY,
		//info
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(5, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(8, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.BEFORE_1_9),
		//who used
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersion.MINECRAFT_1_11_1)
	),
	ITEM_FRAME(WatchedType.ITEM_FRAME, SpecificRemapper.ENTITY,
		//item
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(5, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(8, ValueRemapperNoOp.ITEMSTACK, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(2, ValueRemapperNoOp.ITEMSTACK, ProtocolVersionsHelper.BEFORE_1_8),
		//rotation
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
		.addRemap(9, ValueRemapperNumberToByte.INSTANCE, ProtocolVersion.MINECRAFT_1_8)
		.addRemap(3, new ValueRemapper<DataWatcherObjectVarInt>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (object.getValue() >> 1));
			}
		}, ProtocolVersionsHelper.BEFORE_1_8)
	),
	ENDER_CRYSTAL(WatchedType.ENDER_CRYSTAL, SpecificRemapper.ENTITY,
		//target
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.OPTIONAL_POSITION, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(5, ValueRemapperNoOp.OPTIONAL_POSITION, ProtocolVersionsHelper.ALL_1_9),
		//show botton
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(6, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9)
	),
	ARMOR_STAND_OBJECT(WatchedType.ARMOR_STAND_OBJECT, SpecificRemapper.ARMOR_STAND),
	AREA_EFFECT_CLOUD(WatchedType.AREA_EFFECT_CLOUD, SpecificRemapper.ENTITY,
		//radius
		new Mapping(6)
		.addRemap(6, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(5, ValueRemapperNoOp.FLOAT, ProtocolVersionsHelper.ALL_1_9),
		//color
		new Mapping(7)
		.addRemap(7, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(6, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9),
		//single point
		new Mapping(8)
		.addRemap(8, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(7, ValueRemapperNoOp.BOOLEAN, ProtocolVersionsHelper.ALL_1_9),
		//particle id
		new Mapping(9)
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(8, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9),
		//particle param 1
		new Mapping(10)
		.addRemap(10, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(9, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9),
		//particle param 2
		new Mapping(11)
		.addRemap(11, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.RANGE__1_10__1_11)
		.addRemap(10, ValueRemapperNoOp.VARINT, ProtocolVersionsHelper.ALL_1_9)
	),
	SHULKER_BULLET(WatchedType.SHULKER_BULLET, SpecificRemapper.ENTITY),
	DRAGON_FIREBALL(WatchedType.DRAGON_FIREBALL, SpecificRemapper.ENTITY),
	EVOCATOR_FANGS(WatchedType.EVOCATOR_FANGS, SpecificRemapper.ENTITY);

	private static final EnumMap<WatchedType, SpecificRemapper> wtype = new EnumMap<>(WatchedType.class);

	static {
		for (SpecificRemapper remapper : SpecificRemapper.values()) {
			wtype.put(remapper.type, remapper);
		}
	}

	public static SpecificRemapper fromWatchedType(WatchedType type) {
		return wtype.getOrDefault(type, SpecificRemapper.NONE);
	}

	private final WatchedType type;
	private final EnumMap<ProtocolVersion, ArrayList<MappingEntry>> entries = new EnumMap<>(ProtocolVersion.class);
	{
		for (ProtocolVersion version : ProtocolVersion.values()) {
			entries.put(version, new ArrayList<MappingEntry>());
		}
	}

	SpecificRemapper(WatchedType type, Mapping... entries) {
		this.type = type;
		for (Mapping mapping : entries) {
			for (Mapping.Entry entry : mapping.entries) {
				for (ProtocolVersion version : entry.versions) {
					this.entries.get(version).add(new MappingEntry(mapping.idFrom, entry.idTo, entry.vremap));
				}
			}
		}
	}

	SpecificRemapper(WatchedType type, SpecificRemapper superType, Mapping... entries) {
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
