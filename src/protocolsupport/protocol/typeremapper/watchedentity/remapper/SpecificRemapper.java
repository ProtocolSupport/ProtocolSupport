package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.EntityType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.MappingEntry.MappingEntryOriginal;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperBooleanToByte;
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
		new Mapping()
		.addEntries(new MappingEntryOriginal(0))
		.addProtocols(ProtocolVersionsHelper.ALL),
		//air
		new Mapping()
		.addEntries(new MappingEntry(1, ValueRemapperNumberToShort.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	LIVING(EType.NONE, -1, SpecificRemapper.ENTITY,
		//nametag
		new Mapping()
		.addEntries(new MappingEntryOriginal(2))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping()
		.addEntries(new MappingEntry(2, 10, new ValueRemapperStringClamp(64)))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_1)),
		new Mapping()
		.addEntries(new MappingEntry(2, 5, new ValueRemapperStringClamp(64)))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6),
		//nametag visible
		new Mapping()
		.addEntries(new MappingEntry(3, 3, ValueRemapperBooleanToByte.INSTANCE))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping()
		.addEntries(new MappingEntry(3, 11, ValueRemapperBooleanToByte.INSTANCE))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_1)),
		new Mapping()
		.addEntries(new MappingEntry(3, 6, ValueRemapperBooleanToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6),
		//health
		new Mapping()
		.addEntries(new MappingEntryOriginal(6))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		//pcolor, pambient, arrowsn
		new Mapping()
		.addEntries(new MappingEntry(7, 7, ValueRemapperNumberToInt.INSTANCE))
		.addEntries(new MappingEntry(8, ValueRemapperBooleanToByte.INSTANCE))
		.addEntries(new MappingEntry(9, 9, ValueRemapperNumberToByte.INSTANCE))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_1)),
		new Mapping()
		.addEntries(new MappingEntry(7, 8, ValueRemapperNumberToInt.INSTANCE))
		.addEntries(new MappingEntry(8, 9, ValueRemapperBooleanToByte.INSTANCE))
		.addEntries(new MappingEntry(9, 10, ValueRemapperNumberToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6)
	),
	INSENTIENT(EType.NONE, -1, SpecificRemapper.LIVING,
		//noai
		new Mapping()
		.addEntries(new MappingEntry(10, 15))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8)
	),
	PLAYER(EType.NONE, -1, SpecificRemapper.LIVING,
		//abs hearts, score
		new Mapping()
		.addEntries(new MappingEntry(10, 17))
		.addEntries(new MappingEntry(11, 18, ValueRemapperNumberToInt.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//skin flags(cape enabled for some protocols)
		new Mapping()
		.addEntries(new MappingEntry(12, 10))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1))
	),
	AGEABLE(EType.NONE, -1, SpecificRemapper.INSENTIENT,
		//age
		new Mapping()
		.addEntries(new MappingEntry(11, 12, new ValueRemapper<DataWatcherObjectBoolean>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectByte((byte) (object.getValue() ? -1 : 0));
			}
		}))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping()
		.addEntries(new MappingEntry(11, 12, new ValueRemapper<DataWatcherObjectBoolean>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectBoolean object) {
				return new DataWatcherObjectInt((object.getValue() ? -1 : 0));
			}
		}))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_8),
		//age - special hack for hologram plugins that want to set int age
		//datawatcher index 30 will be remapped to age datawatcher index
		new Mapping()
		.addEntries(new MappingEntry(30, 12, ValueRemapperNumberToInt.INSTANCE))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_1))
	),
	TAMEABLE(EType.NONE, -1, SpecificRemapper.AGEABLE,
		//tame flags
		new Mapping()
		.addEntries(new MappingEntry(12, 16))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ARMOR_STAND(EType.NONE, -1, SpecificRemapper.LIVING,
		//parts position
		new Mapping()
		.addEntries(MappingEntryOriginal.of(10, 11, 12, 13, 14, 15, 16))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8)
	),
	COW(EType.MOB, EntityType.COW, SpecificRemapper.AGEABLE),
	MUSHROOM_COW(EType.MOB, EntityType.MUSHROOM_COW, SpecificRemapper.COW),
	CHICKEN(EType.MOB, EntityType.CHICKEN, SpecificRemapper.AGEABLE),
	SQUID(EType.MOB, EntityType.SQUID, SpecificRemapper.INSENTIENT),
	HORSE(EType.MOB, EntityType.HORSE, SpecificRemapper.AGEABLE,
		//info flags, type, color/variant, armor
		new Mapping()
		.addEntries(new MappingEntry(12, 16, ValueRemapperNumberToInt.INSTANCE))
		.addEntries(new MappingEntry(13, 19, ValueRemapperNumberToByte.INSTANCE))
		.addEntries(new MappingEntry(14, 20, ValueRemapperNumberToInt.INSTANCE))
		.addEntries(new MappingEntry(16, 22, ValueRemapperNumberToInt.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	BAT(EType.MOB, EntityType.BAT, SpecificRemapper.INSENTIENT,
		//hanging
		new Mapping()
		.addEntries(new MappingEntry(11, 16))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	OCELOT(EType.MOB, EntityType.OCELOT, SpecificRemapper.TAMEABLE,
		//type
		new Mapping()
		.addEntries(new MappingEntry(14, 18, ValueRemapperNumberToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	WOLF(EType.MOB, EntityType.WOLF, SpecificRemapper.TAMEABLE,
		//health
		new Mapping()
		.addEntries(new MappingEntry(14, 18))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Mapping()
		.addEntries(new MappingEntry(14, 18, ValueRemapperNumberToInt.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6),
		//begging
		new Mapping()
		.addEntries(new MappingEntry(15, 19, ValueRemapperBooleanToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//collar color
		new Mapping()
		.addEntries(new MappingEntry(16, 20, ValueRemapperNumberToByte.INSTANCE))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping()
		.addEntries(new MappingEntry(16, 20, new ValueRemapper<DataWatcherObjectVarInt>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (15 - object.getValue()));
			}
		}))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_8)
	),
	PIG(EType.MOB, EntityType.PIG, SpecificRemapper.AGEABLE,
		//has saddle
		new Mapping()
		.addEntries(new MappingEntry(12, 16, ValueRemapperBooleanToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	RABBIT(EType.MOB, EntityType.RABBIT, SpecificRemapper.AGEABLE,
		//type
		new Mapping()
		.addEntries(new MappingEntry(12, 18, ValueRemapperNumberToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHEEP(EType.MOB, EntityType.SHEEP, SpecificRemapper.AGEABLE,
		//info flags (color + sheared)
		new Mapping()
		.addEntries(new MappingEntry(12, 16))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	VILLAGER(EType.MOB, EntityType.VILLAGER, SpecificRemapper.AGEABLE,
		//profession
		new Mapping()
		.addEntries(new MappingEntry(12, 16, ValueRemapperNumberToInt.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ENDERMAN(EType.MOB, EntityType.ENDERMAN, SpecificRemapper.INSENTIENT,
		//carried block
		new Mapping()
		.addEntries(new MappingEntry(11, 16, new ValueRemapper<DataWatcherObjectBlockState>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectBlockState object) {
				return new DataWatcherObjectShort((short) (object.getValue() >> 4));
			}
		}))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping()
		.addEntries(new MappingEntry(11, 16, new ValueRemapper<DataWatcherObjectBlockState>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectBlockState object) {
				return new DataWatcherObjectByte((byte) (object.getValue() >> 4));
			}
		}))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_8),
		new Mapping()
		.addEntries(new MappingEntry(11, 17, new ValueRemapper<DataWatcherObjectBlockState>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectBlockState object) {
				return new DataWatcherObjectByte((byte) (object.getValue() & 0xF));
			}
		}))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//screaming
		new Mapping()
		.addEntries(new MappingEntry(12, 18, ValueRemapperBooleanToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	GIANT(EType.MOB, EntityType.GIANT, SpecificRemapper.INSENTIENT),
	SILVERFISH(EType.MOB, EntityType.SILVERFISH, SpecificRemapper.INSENTIENT),
	ENDERMITE(EType.MOB, EntityType.ENDERMITE, SpecificRemapper.INSENTIENT),
	ENDER_DRAGON(EType.MOB, EntityType.ENDER_DRAGON, SpecificRemapper.INSENTIENT),
	SNOWMAN(EType.MOB, EntityType.SNOWMAN, SpecificRemapper.INSENTIENT),
	ZOMBIE(EType.MOB, EntityType.ZOMBIE, SpecificRemapper.INSENTIENT,
		//is baby, is villager, is converting
		new Mapping()
		.addEntries(new MappingEntry(11, 12, ValueRemapperBooleanToByte.INSTANCE))
		.addEntries(new MappingEntry(12, 13, ValueRemapperNumberToByte.INSTANCE))
		.addEntries(new MappingEntry(13, 14, ValueRemapperBooleanToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ZOMBIE_PIGMAN(EType.MOB, EntityType.PIG_ZOMBIE, SpecificRemapper.ZOMBIE),
	BLAZE(EType.MOB, EntityType.BLAZE, SpecificRemapper.INSENTIENT,
		//on fire
		new Mapping()
		.addEntries(new MappingEntry(11, 16))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPIDER(EType.MOB, EntityType.SPIDER, SpecificRemapper.LIVING,
		//is climbing
		new Mapping()
		.addEntries(new MappingEntry(11, 16))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	CAVE_SPIDER(EType.MOB, EntityType.CAVE_SPIDER, SpecificRemapper.SPIDER),
	CREEPER(EType.MOB, EntityType.CREEPER, SpecificRemapper.INSENTIENT,
		//state, is powered, ignited
		new Mapping()
		.addEntries(new MappingEntry(11, 16, ValueRemapperNumberToByte.INSTANCE))
		.addEntries(new MappingEntry(12, 17, ValueRemapperBooleanToByte.INSTANCE))
		.addEntries(new MappingEntry(13, 18, ValueRemapperBooleanToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	GHAST(EType.MOB, EntityType.GHAST, SpecificRemapper.INSENTIENT,
		//is attacking
		new Mapping()
		.addEntries(new MappingEntry(11, 16, ValueRemapperBooleanToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	SLIME(EType.MOB, EntityType.SLIME, SpecificRemapper.INSENTIENT,
		//size
		new Mapping()
		.addEntries(new MappingEntry(11, 16, ValueRemapperNumberToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	MAGMA_CUBE(EType.MOB, EntityType.MAGMA_CUBE, SpecificRemapper.SLIME),
	SKELETON(EType.MOB, EntityType.SKELETON, SpecificRemapper.INSENTIENT,
		//type
		new Mapping()
		.addEntries(new MappingEntry(11, 13, ValueRemapperNumberToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	WITCH(EType.MOB, EntityType.WITCH, SpecificRemapper.INSENTIENT,
		//agressive
		new Mapping()
		.addEntries(new MappingEntry(11, 21, ValueRemapperBooleanToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	IRON_GOLEM(EType.MOB, EntityType.IRON_GOLEM, SpecificRemapper.INSENTIENT,
		//player created
		new Mapping()
		.addEntries(new MappingEntry(11, 16))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHULKER(EType.MOB, 69, SpecificRemapper.INSENTIENT),
	WITHER(EType.MOB, EntityType.WITHER, SpecificRemapper.INSENTIENT,
		//target 1-3, invulnerable time
		new Mapping()
		.addEntries(new MappingEntry(11, 17, ValueRemapperNumberToInt.INSTANCE))
		.addEntries(new MappingEntry(12, 18, ValueRemapperNumberToInt.INSTANCE))
		.addEntries(new MappingEntry(13, 19, ValueRemapperNumberToInt.INSTANCE))
		.addEntries(new MappingEntry(14, 20, ValueRemapperNumberToInt.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	GUARDIAN(EType.MOB, EntityType.GUARDIAN, SpecificRemapper.INSENTIENT,
		//info flags(elder, spikes), target id
		new Mapping()
		.addEntries(new MappingEntry(11, 16, ValueRemapperNumberToInt.INSTANCE))
		.addEntries(new MappingEntry(12, 17, ValueRemapperNumberToInt.INSTANCE))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8)
	),
	ARMOR_STAND_MOB(EType.MOB, EntityType.ARMOR_STAND, SpecificRemapper.ARMOR_STAND),
	BOAT(EType.OBJECT, 1,
		//time since hit, forward direction
		new Mapping()
		.addEntries(new MappingEntry(5, 17, ValueRemapperNumberToInt.INSTANCE))
		.addEntries(new MappingEntry(6, 18, ValueRemapperNumberToInt.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//damage taken
		new Mapping()
		.addEntries(new MappingEntry(7, 19))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Mapping()
		.addEntries(new MappingEntry(7, 19, ValueRemapperNumberToInt.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6)
	),
	TNT(EType.OBJECT, 50, SpecificRemapper.ENTITY),
	SNOWBALL(EType.OBJECT, 61, SpecificRemapper.ENTITY),
	EGG(EType.OBJECT, 62, SpecificRemapper.ENTITY),
	FIREBALL(EType.OBJECT, 63, SpecificRemapper.ENTITY),
	FIRECHARGE(EType.OBJECT, 64, SpecificRemapper.ENTITY),
	ENDERPEARL(EType.OBJECT, 65, SpecificRemapper.ENTITY),
	WITHER_SKULL(EType.OBJECT, 66, SpecificRemapper.FIREBALL,
		//is charged
		new Mapping()
		.addEntries(new MappingEntry(5, 10, ValueRemapperBooleanToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
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
		new Mapping()
		.addEntries(new MappingEntry(5, 10))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART(EType.OBJECT, 10, SpecificRemapper.ENTITY,
		//shaking power, shaking direction, block y, show block
		new Mapping()
		.addEntries(new MappingEntry(5, 17, ValueRemapperNumberToInt.INSTANCE))
		.addEntries(new MappingEntry(6, 18, ValueRemapperNumberToInt.INSTANCE))
		.addEntries(new MappingEntry(9, 21, ValueRemapperNumberToInt.INSTANCE))
		.addEntries(new MappingEntry(10, 22, ValueRemapperBooleanToByte.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//damage taken
		new Mapping()
		.addEntries(new MappingEntry(7, 19))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Mapping()
		.addEntries(new MappingEntry(7, 19, ValueRemapperNumberToInt.INSTANCE))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6),
		//block
		new Mapping()
		.addEntries(new MappingEntry(8, 20, ValueRemapperNumberToInt.INSTANCE))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping()
		.addEntries(new MappingEntry(8, 20, new ValueRemapper<DataWatcherObjectVarInt>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectVarInt object) {
				int value = object.getValue();
				int id = value & 0xFFFF;
				int data = value >> 12;
				return new DataWatcherObjectInt((data << 16) | id);
			}
		})).addProtocols(ProtocolVersionsHelper.BEFORE_1_6)
	),
	ARROW(EType.OBJECT, 60, SpecificRemapper.ENTITY,
		//is critical
		new Mapping()
		.addEntries(new MappingEntry(5, 16))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPECTRAL_ARROW(EType.OBJECT, 91, SpecificRemapper.ARROW),
	TIPPED_ARROW(EType.OBJECT, 92, SpecificRemapper.ARROW),
	FIREWORK(EType.OBJECT, 76, SpecificRemapper.ENTITY,
		//info
		new Mapping()
		.addEntries(new MappingEntry(5, 8))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ITEM_FRAME(EType.OBJECT, 71, SpecificRemapper.ENTITY,
		//item, rotation
		new Mapping()
		.addEntries(new MappingEntry(5, 8))
		.addEntries(new MappingEntry(6, 9, ValueRemapperNumberToByte.INSTANCE))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping()
		.addEntries(new MappingEntry(5, 2))
		.addEntries(new MappingEntry(6, 3, new ValueRemapper<DataWatcherObjectVarInt>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectVarInt object) {
				return new DataWatcherObjectByte((byte) (object.getValue() >> 1));
			}
		})).addProtocols(ProtocolVersionsHelper.BEFORE_1_8)
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
		this.etype = etype;
		this.typeId = typeId;
		for (Mapping rp : entries) {
			for (ProtocolVersion version : rp.versions) {
				this.entries.get(version).addAll(rp.entries);
			}
		}
	}

	@SuppressWarnings("deprecation")
	SpecificRemapper(EType etype, EntityType type, SpecificRemapper superType, Mapping... entries) {
		this(etype, type.getTypeId(), superType, entries);
	}

	SpecificRemapper(EType etype, int typeId, SpecificRemapper superType, Mapping... entries) {
		this.etype = etype;
		this.typeId = typeId;
		for (Entry<ProtocolVersion, ArrayList<MappingEntry>> entry : superType.entries.entrySet()) {
			this.entries.get(entry.getKey()).addAll(entry.getValue());
		}
		for (Mapping rp : entries) {
			for (ProtocolVersion version : rp.versions) {
				this.entries.get(version).addAll(rp.entries);
			}
		}
	}

	public List<MappingEntry> getRemaps(ProtocolVersion version) {
		return entries.get(version);
	}

	private enum EType {
		NONE, OBJECT, MOB
	}

	private static class Mapping {
		private final ArrayList<ProtocolVersion> versions = new ArrayList<>();
		private final ArrayList<MappingEntry> entries = new ArrayList<>();
		protected Mapping(MappingEntry... entries) {
			this.entries.addAll(Arrays.asList(entries));
		}
		protected Mapping addEntries(MappingEntry... entries) {
			this.entries.addAll(Arrays.asList(entries));
			return this;
		}
		protected Mapping addProtocols(ProtocolVersion... versions) {
			this.versions.addAll(Arrays.asList(versions));
			return this;
		}
	}

}
