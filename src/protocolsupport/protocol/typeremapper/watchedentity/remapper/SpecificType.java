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
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperNumberToByte;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperNumberToInt;
import protocolsupport.utils.ProtocolVersionsHelper;
import protocolsupport.utils.datawatcher.DataWatcherObject;
import protocolsupport.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.utils.datawatcher.objects.DataWatcherObjectInt;

public enum SpecificType {

	NONE(EType.NONE, -1),
	ENTITY(EType.NONE, -1,
		//flags
		new Mapping()
		.addEntries(new MappingEntryOriginal(0))
		.addProtocols(ProtocolVersionsHelper.ALL),
		//air
		new Mapping()
		.addEntries(new MappingEntry(1, new ValueRemapperNumberToInt()))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	LIVING(EType.NONE, -1, SpecificType.ENTITY,
		//nametag
		new Mapping()
		.addEntries(new MappingEntryOriginal(2))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping()
		.addEntries(new MappingEntry(2, 10, new ValueRemapperStringClamp(64)))
		.addEntries(new MappingEntry(3, 11))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_1)),
		new Mapping()
		.addEntries(new MappingEntry(2, 5, new ValueRemapperStringClamp(64)))
		.addEntries(new MappingEntry(3, 6))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6),
		//nametag visible
		new Mapping()
		.addEntries(new MappingEntry(3, 3, new ValueRemapperBooleanToByte()))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping()
		.addEntries(new MappingEntry(3, 11, new ValueRemapperBooleanToByte()))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_1)),
		new Mapping()
		.addEntries(new MappingEntry(3, 6, new ValueRemapperBooleanToByte()))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6),
		//health
		new Mapping()
		.addEntries(new MappingEntryOriginal(6))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		//pcolor, pambient, arrowsn
		new Mapping()
		.addEntries(new MappingEntry(7, 7, new ValueRemapperNumberToInt()))
		.addEntries(new MappingEntryOriginal(8))
		.addEntries(new MappingEntry(9, 9, new ValueRemapperNumberToByte()))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_1)),
		new Mapping()
		.addEntries(new MappingEntry(7, 8, new ValueRemapperNumberToInt()))
		.addEntries(new MappingEntry(8, 9))
		.addEntries(new MappingEntry(9, 10, new ValueRemapperNumberToByte()))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6),
		//noai
		new Mapping()
		.addEntries(new MappingEntry(15, 10))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8)
	),
	//TODO: No info for player, update when spigot 1.9 is out
	PLAYER(EType.NONE, -1, SpecificType.LIVING,
		//abs hearts, score
		new Mapping(MappingEntryOriginal.of(17, 18)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//skin flags(cape enabled for some protocols)
		new Mapping(new MappingEntryOriginal(10))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1))
	),
	AGEABLE(EType.NONE, -1, SpecificType.LIVING,
		//age
		new Mapping()
		.addEntries(new MappingEntry(11, 12))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping()
		.addEntries(new MappingEntry(11, 12, new ValueRemapperNumberToInt()))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_8)
	),
	TAMEABLE(EType.NONE, -1, SpecificType.AGEABLE,
		//tame flags
		new Mapping()
		.addEntries(new MappingEntry(12, 16))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ARMOR_STAND(EType.NONE, -1, SpecificType.LIVING,
		//parts position
		new Mapping()
		.addEntries(MappingEntryOriginal.of(10, 11, 12, 13, 14, 15, 16))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8)
	),
	COW(EType.MOB, EntityType.COW, SpecificType.AGEABLE),
	MUSHROOM_COW(EType.MOB, EntityType.MUSHROOM_COW, SpecificType.COW),
	CHICKEN(EType.MOB, EntityType.CHICKEN, SpecificType.AGEABLE),
	SQUID(EType.MOB, EntityType.SQUID, SpecificType.LIVING),
	HORSE(EType.MOB, EntityType.HORSE, SpecificType.AGEABLE,
		//info flags, type, color/variant, armor
		new Mapping()
		.addEntries(new MappingEntry(12, 16, new ValueRemapperNumberToInt()))
		.addEntries(new MappingEntry(13, 19, new ValueRemapperNumberToByte()))
		.addEntries(new MappingEntry(14, 20, new ValueRemapperNumberToInt()))
		.addEntries(new MappingEntry(16, 22, new ValueRemapperNumberToInt()))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	BAT(EType.MOB, EntityType.BAT, SpecificType.LIVING,
		//hanging
		new Mapping()
		.addEntries(new MappingEntry(11, 16))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	OCELOT(EType.MOB, EntityType.OCELOT, SpecificType.TAMEABLE,
		//type
		new Mapping()
		.addEntries(new MappingEntry(14, 18, new ValueRemapperNumberToByte()))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	WOLF(EType.MOB, EntityType.WOLF, SpecificType.TAMEABLE,
		//health
		new Mapping()
		.addEntries(new MappingEntry(14, 18))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Mapping()
		.addEntries(new MappingEntry(14, 18, new ValueRemapperNumberToInt()))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6),
		//begging
		new Mapping()
		.addEntries(new MappingEntry(15, 19, new ValueRemapperBooleanToByte()))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//collar color
		new Mapping()
		.addEntries(new MappingEntry(16, 20, new ValueRemapperNumberToByte()))
		.addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping()
		.addEntries(new MappingEntry(16, 20, new ValueRemapper<DataWatcherObjectByte>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectByte object) {
				return new DataWatcherObjectByte((byte) (15 - object.getValue()));
			}
		}))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_8)
	),
	//TODO: new remap
	PIG(EType.MOB, EntityType.PIG, SpecificType.AGEABLE,
		//has saddle
		new Mapping(new MappingEntryOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	RABBIT(EType.MOB, EntityType.RABBIT, SpecificType.AGEABLE,
		//type
		new Mapping(new MappingEntryOriginal(18)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHEEP(EType.MOB, EntityType.SHEEP, SpecificType.AGEABLE,
		//info flags (color + sheared)
		new Mapping(new MappingEntryOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	VILLAGER(EType.MOB, EntityType.VILLAGER, SpecificType.AGEABLE,
		//profession
		new Mapping(new MappingEntryOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ENDERMAN(EType.MOB, EntityType.ENDERMAN, SpecificType.LIVING,
		//carried data id, screaming
		new Mapping(MappingEntryOriginal.of(17, 18)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//carried block id
		new Mapping(new MappingEntryOriginal(16)).addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping(new MappingEntry(16, 16, new ValueRemapperNumberToByte()))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_8)
	),
	GIANT(EType.MOB, EntityType.GIANT, SpecificType.LIVING),
	SILVERFISH(EType.MOB, EntityType.SILVERFISH, SpecificType.LIVING),
	ENDERMITE(EType.MOB, EntityType.ENDERMITE, SpecificType.SILVERFISH),
	ENDER_DRAGON(EType.MOB, EntityType.ENDER_DRAGON, SpecificType.LIVING),
	SNOWMAN(EType.MOB, EntityType.SNOWMAN, SpecificType.LIVING),
	ZOMBIE(EType.MOB, EntityType.ZOMBIE, SpecificType.LIVING,
		//is baby, is villager, is converting
		new Mapping(MappingEntryOriginal.of(12, 13, 14)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ZOMBIE_PIGMAN(EType.MOB, EntityType.PIG_ZOMBIE, SpecificType.ZOMBIE),
	BLAZE(EType.MOB, EntityType.BLAZE, SpecificType.LIVING,
		//on fire
		new Mapping(new MappingEntryOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPIDER(EType.MOB, EntityType.SPIDER, SpecificType.LIVING,
		//is climbing
		new Mapping(new MappingEntryOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	CAVE_SPIDER(EType.MOB, EntityType.CAVE_SPIDER, SpecificType.SPIDER),
	CREEPER(EType.MOB, EntityType.CREEPER, SpecificType.LIVING,
		//state, is powered, ignited
		new Mapping(MappingEntryOriginal.of(16, 17, 18)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	GHAST(EType.MOB, EntityType.GHAST, SpecificType.LIVING,
		//is attacking
		new Mapping(new MappingEntryOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	SLIME(EType.MOB, EntityType.SLIME, SpecificType.LIVING,
		//size
		new Mapping(new MappingEntryOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	MAGMA_CUBE(EType.MOB, EntityType.MAGMA_CUBE, SpecificType.SLIME),
	SKELETON(EType.MOB, EntityType.SKELETON, SpecificType.LIVING,
		//type
		new Mapping(new MappingEntryOriginal(13)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	WITCH(EType.MOB, EntityType.WITCH, SpecificType.LIVING,
		//agressive
		new Mapping(new MappingEntryOriginal(21)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	IRON_GOLEM(EType.MOB, EntityType.IRON_GOLEM, SpecificType.LIVING,
		//player created
		new Mapping(new MappingEntryOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	WITHER(EType.MOB, EntityType.WITHER, SpecificType.LIVING,
		//target 1-3, invulnerable time
		new Mapping(MappingEntryOriginal.of(17, 18, 19, 20)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	GUARDIAN(EType.MOB, EntityType.GUARDIAN, SpecificType.LIVING,
		//info flags(elder, spikes), target id
		new Mapping(MappingEntryOriginal.of(16, 17)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ARMOR_STAND_MOB(EType.MOB, EntityType.ARMOR_STAND, SpecificType.ARMOR_STAND),
	BOAT(EType.OBJECT, 1,
		//time since hit, forward direction
		new Mapping(MappingEntryOriginal.of(17, 18)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//damage taken
		new Mapping(new MappingEntryOriginal(19))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Mapping(new MappingEntry(19, 19, new ValueRemapperNumberToInt()))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6)
	),
	TNT(EType.OBJECT, 50, SpecificType.ENTITY),
	SNOWBALL(EType.OBJECT, 61, SpecificType.ENTITY),
	EGG(EType.OBJECT, 62, SpecificType.ENTITY),
	FIREBALL(EType.OBJECT, 63, SpecificType.ENTITY),
	FIRECHARGE(EType.OBJECT, 64, SpecificType.ENTITY),
	ENDERPEARL(EType.OBJECT, 65, SpecificType.ENTITY),
	WITHER_SKULL(EType.OBJECT, 66, SpecificType.FIREBALL,
		//is charged
		new Mapping(new MappingEntryOriginal(10)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	FALLING_OBJECT(EType.OBJECT, 70, SpecificType.ENTITY),
	ENDEREYE(EType.OBJECT, 72, SpecificType.ENTITY),
	POTION(EType.OBJECT, 73, SpecificType.ENTITY),
	DRAGON_EGG(EType.OBJECT, 74, SpecificType.ENTITY),
	EXP_BOTTLE(EType.OBJECT, 75, SpecificType.ENTITY),
	LEASH_KNOT(EType.OBJECT, 77, SpecificType.ENTITY),
	FISHING_FLOAT(EType.OBJECT, 90, SpecificType.ENTITY),
	ITEM(EType.OBJECT, 2, SpecificType.ENTITY,
		//item
		new Mapping(new MappingEntryOriginal(10)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART(EType.OBJECT, 10, SpecificType.ENTITY,
		//is powered, shaking power, shaking direction, show block
		new Mapping(MappingEntryOriginal.of(16, 17, 18, 21, 22)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//damage taken
		new Mapping(new MappingEntryOriginal(19))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new Mapping(new MappingEntry(19, 19, new ValueRemapperNumberToInt())).addProtocols(ProtocolVersionsHelper.BEFORE_1_6),
		//block
		new Mapping(new MappingEntryOriginal(20)).addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping(new MappingEntry(20, 20, new ValueRemapper<DataWatcherObjectInt>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectInt object) {
				int value = object.getValue();
				int id = value & 0xFFFF;
				int data = value >> 12;
				return new DataWatcherObjectInt((data << 16) | id);
			}
		})).addProtocols(ProtocolVersionsHelper.BEFORE_1_6)
	),
	ARROW(EType.OBJECT, 60, SpecificType.ENTITY,
		//is critical
		new Mapping(new MappingEntryOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	FIREWORK(EType.OBJECT, 76, SpecificType.ENTITY,
		//info
		new Mapping(new MappingEntryOriginal(8)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ITEM_FRAME(EType.OBJECT, 71, SpecificType.ENTITY,
		//item, rotation
		new Mapping(MappingEntryOriginal.of(8, 9)).addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new Mapping(new MappingEntry(8, 2), new MappingEntry(9, 3, new ValueRemapper<DataWatcherObjectByte>() {
			@Override
			public DataWatcherObject<?> remap(DataWatcherObjectByte object) {
				return new DataWatcherObjectByte((byte) (object.getValue() >> 1));
			}
		})).addProtocols(ProtocolVersionsHelper.BEFORE_1_8)
	),
	ENDER_CRYSTAL(EType.OBJECT, 51, SpecificType.ENTITY,
		//health
		new Mapping(new MappingEntryOriginal(8)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ARMOR_STAND_OBJECT(EType.OBJECT, 78, SpecificType.ARMOR_STAND);


	private static final SpecificType[] OBJECT_BY_TYPE_ID = new SpecificType[256];
	private static final SpecificType[] MOB_BY_TYPE_ID = new SpecificType[256];

	static {
		Arrays.fill(OBJECT_BY_TYPE_ID, SpecificType.NONE);
		Arrays.fill(MOB_BY_TYPE_ID, SpecificType.NONE);
		for (SpecificType type : values()) {
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

	public static SpecificType getObjectByTypeId(int objectTypeId) {
		return OBJECT_BY_TYPE_ID[objectTypeId];
	}

	public static SpecificType getMobByTypeId(int mobTypeId) {
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
	SpecificType(EType etype, EntityType type, Mapping... entries) {
		this(etype, type.getTypeId(), entries);
	}

	SpecificType(EType etype, int typeId, Mapping... entries) {
		this.etype = etype;
		this.typeId = typeId;
		for (Mapping rp : entries) {
			for (ProtocolVersion version : rp.versions) {
				this.entries.get(version).addAll(rp.entries);
			}
		}
	}

	@SuppressWarnings("deprecation")
	SpecificType(EType etype, EntityType type, SpecificType superType, Mapping... entries) {
		this(etype, type.getTypeId(), superType, entries);
	}

	SpecificType(EType etype, int typeId, SpecificType superType, Mapping... entries) {
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
