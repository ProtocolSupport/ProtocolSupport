package protocolsupport.protocol.typeremapper.watchedentity.remapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.EntityType;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.RemappingEntry.RemappingEntryCopyOriginal;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperStringClamp;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperToByte;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.value.ValueRemapperToInt;
import protocolsupport.utils.DataWatcherObject;
import protocolsupport.utils.ProtocolVersionsHelper;

public enum SpecificType {

	NONE(EType.NONE, -1),
	ENTITY(EType.NONE, -1,
		//flags, air
		new RemappingEntriesForProtocols(RemappingEntryCopyOriginal.of(0, 1)).addProtocols(ProtocolVersionsHelper.ALL)
	),
	LIVING(EType.NONE, -1, SpecificType.ENTITY,
		//nametag, nametagvisible
		new RemappingEntriesForProtocols(RemappingEntryCopyOriginal.of(2, 3)).addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new RemappingEntriesForProtocols(new RemappingEntry(2, 10, new ValueRemapperStringClamp(64)), new RemappingEntry(3, 11))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_1)),
		new RemappingEntriesForProtocols(new RemappingEntry(2, 5, new ValueRemapperStringClamp(64)), new RemappingEntry(3, 6))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6),
		//health
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(6))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		//pcolor, pambient, arrowsn
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(7), new RemappingEntryCopyOriginal(8), new RemappingEntryCopyOriginal(9))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_7_10, ProtocolVersion.MINECRAFT_1_6_1)),
		new RemappingEntriesForProtocols(new RemappingEntry(7, 8), new RemappingEntry(8, 9), new RemappingEntry(9, 10))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6),
		//noai
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(15)).addProtocols(ProtocolVersion.MINECRAFT_1_8)
	),
	PLAYER(EType.NONE, -1, SpecificType.LIVING,
		//abs hearts, score
		new RemappingEntriesForProtocols(RemappingEntryCopyOriginal.of(17, 18)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//skin flags(cape enabled for some protocols)
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(10))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1))
	),
	AGEABLE(EType.NONE, -1, SpecificType.LIVING,
		//age
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(12)).addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new RemappingEntriesForProtocols(new RemappingEntry(12, 12, new ValueRemapperToInt()))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_8)
	),
	TAMEABLE(EType.NONE, -1, SpecificType.AGEABLE,
		//tame flags, owner
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(16), new RemappingEntryCopyOriginal(17)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ARMOR_STAND(EType.NONE, -1, SpecificType.LIVING,
		//parts position
		new RemappingEntriesForProtocols(RemappingEntryCopyOriginal.of(10, 11, 12, 13, 14, 15, 16)).addProtocols(ProtocolVersion.MINECRAFT_1_8)
	),
	COW(EType.MOB, EntityType.COW, SpecificType.AGEABLE),
	MUSHROOM_COW(EType.MOB, EntityType.MUSHROOM_COW, SpecificType.COW),
	CHICKEN(EType.MOB, EntityType.CHICKEN, SpecificType.AGEABLE),
	SQUID(EType.MOB, EntityType.SQUID, SpecificType.LIVING),
	HORSE(EType.MOB, EntityType.HORSE, SpecificType.AGEABLE,
		//info flags, type, color/variant, owner, armor
		new RemappingEntriesForProtocols(RemappingEntryCopyOriginal.of(16, 19, 20, 21, 22)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	BAT(EType.MOB, EntityType.BAT, SpecificType.LIVING,
		//hanging
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	OCELOT(EType.MOB, EntityType.OCELOT, SpecificType.TAMEABLE,
		//type
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(18)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	WOLF(EType.MOB, EntityType.WOLF, SpecificType.TAMEABLE,
		//begging
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(19)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//collar color
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(20)).addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new RemappingEntriesForProtocols(new RemappingEntry(20, 20, new ValueRemapper() {
			@Override
			public DataWatcherObject remap(DataWatcherObject object) {
				object.value = (byte) (15 - (byte) object.value);
				return object;
			}
		})).addProtocols(ProtocolVersionsHelper.BEFORE_1_8),
		//health
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(18))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new RemappingEntriesForProtocols(new RemappingEntry(18, 18, new ValueRemapperToInt()))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_6)
	),
	PIG(EType.MOB, EntityType.PIG, SpecificType.AGEABLE,
		//has saddle
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	RABBIT(EType.MOB, EntityType.RABBIT, SpecificType.AGEABLE,
		//type
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(18)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	SHEEP(EType.MOB, EntityType.SHEEP, SpecificType.AGEABLE,
		//info flags (color + sheared)
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	VILLAGER(EType.MOB, EntityType.VILLAGER, SpecificType.AGEABLE,
		//profession
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ENDERMAN(EType.MOB, EntityType.ENDERMAN, SpecificType.LIVING,
		//carried data id, screaming
		new RemappingEntriesForProtocols(RemappingEntryCopyOriginal.of(17, 18)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//carried block id
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(16)).addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new RemappingEntriesForProtocols(new RemappingEntry(16, 16, new ValueRemapperToByte()))
		.addProtocols(ProtocolVersionsHelper.BEFORE_1_8)
	),
	GIANT(EType.MOB, EntityType.GIANT, SpecificType.LIVING),
	SILVERFISH(EType.MOB, EntityType.SILVERFISH, SpecificType.LIVING),
	ENDERMITE(EType.MOB, EntityType.ENDERMITE, SpecificType.SILVERFISH),
	ENDER_DRAGON(EType.MOB, EntityType.ENDER_DRAGON, SpecificType.LIVING),
	SNOWMAN(EType.MOB, EntityType.SNOWMAN, SpecificType.LIVING),
	ZOMBIE(EType.MOB, EntityType.ZOMBIE, SpecificType.LIVING,
		//is baby, is villager, is converting
		new RemappingEntriesForProtocols(RemappingEntryCopyOriginal.of(12, 13, 14)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ZOMBIE_PIGMAN(EType.MOB, EntityType.PIG_ZOMBIE, SpecificType.ZOMBIE),
	BLAZE(EType.MOB, EntityType.BLAZE, SpecificType.LIVING,
		//on fire
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	SPIDER(EType.MOB, EntityType.SPIDER, SpecificType.LIVING,
		//is climbing
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	CAVE_SPIDER(EType.MOB, EntityType.CAVE_SPIDER, SpecificType.SPIDER),
	CREEPER(EType.MOB, EntityType.CREEPER, SpecificType.LIVING,
		//state, is powered, ignited
		new RemappingEntriesForProtocols(RemappingEntryCopyOriginal.of(16, 17, 18)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	GHAST(EType.MOB, EntityType.GHAST, SpecificType.LIVING,
		//is attacking
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	SLIME(EType.MOB, EntityType.SLIME, SpecificType.LIVING,
		//size
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	MAGMA_CUBE(EType.MOB, EntityType.MAGMA_CUBE, SpecificType.SLIME),
	SKELETON(EType.MOB, EntityType.SKELETON, SpecificType.LIVING,
		//type
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(13)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	WITCH(EType.MOB, EntityType.WITCH, SpecificType.LIVING,
		//agressive
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(21)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	IRON_GOLEM(EType.MOB, EntityType.IRON_GOLEM, SpecificType.LIVING,
		//player created
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	WITHER(EType.MOB, EntityType.WITHER, SpecificType.LIVING,
		//target 1-3, invulnerable time
		new RemappingEntriesForProtocols(RemappingEntryCopyOriginal.of(17, 18, 19, 20)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	GUARDIAN(EType.MOB, EntityType.GUARDIAN, SpecificType.LIVING,
		//info flags(elder, spikes), target id
		new RemappingEntriesForProtocols(RemappingEntryCopyOriginal.of(16, 17)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ARMOR_STAND_MOB(EType.MOB, EntityType.ARMOR_STAND, SpecificType.ARMOR_STAND),
	BOAT(EType.OBJECT, 1,
		//time since hit, forward direction
		new RemappingEntriesForProtocols(RemappingEntryCopyOriginal.of(17, 18)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//damage taken
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(19))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new RemappingEntriesForProtocols(new RemappingEntry(19, 19, new ValueRemapperToInt()))
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
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(10)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
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
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(10)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	MINECART(EType.OBJECT, 10, SpecificType.ENTITY,
		//is powered, shaking power, shaking direction, show block
		new RemappingEntriesForProtocols(RemappingEntryCopyOriginal.of(16, 17, 18, 21, 22)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9),
		//damage taken
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(19))
		.addProtocols(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_8, ProtocolVersion.MINECRAFT_1_6_1)),
		new RemappingEntriesForProtocols(new RemappingEntry(19, 19, new ValueRemapperToInt())).addProtocols(ProtocolVersionsHelper.BEFORE_1_6),
		//block
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(20)).addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new RemappingEntriesForProtocols(new RemappingEntry(20, 20, new ValueRemapper() {
			@Override
			public DataWatcherObject remap(DataWatcherObject object) {
				int value = (int) object.value;
				int id = value & 0xFFFF;
				int data = value >> 12;
				object.value = (data << 16) | id;
				return object;
			}
		})).addProtocols(ProtocolVersionsHelper.BEFORE_1_6)
	),
	ARROW(EType.OBJECT, 60, SpecificType.ENTITY,
		//is critical
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(16)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	FIREWORK(EType.OBJECT, 76, SpecificType.ENTITY,
		//info
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(8)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
	),
	ITEM_FRAME(EType.OBJECT, 71, SpecificType.ENTITY,
		//item, rotation
		new RemappingEntriesForProtocols(RemappingEntryCopyOriginal.of(8, 9)).addProtocols(ProtocolVersion.MINECRAFT_1_8),
		new RemappingEntriesForProtocols(new RemappingEntry(8, 2), new RemappingEntry(9, 3, new ValueRemapper() {
			@Override
			public DataWatcherObject remap(DataWatcherObject object) {
				int rotation = (byte) object.value;
				object.value = (byte) (rotation >>= 1);
				return object;
			}
		})).addProtocols(ProtocolVersionsHelper.BEFORE_1_8)
	),
	ENDER_CRYSTAL(EType.OBJECT, 51, SpecificType.ENTITY,
		//health
		new RemappingEntriesForProtocols(new RemappingEntryCopyOriginal(8)).addProtocols(ProtocolVersionsHelper.BEFORE_1_9)
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
	private final EnumMap<ProtocolVersion, ArrayList<RemappingEntry>> entries = new EnumMap<ProtocolVersion, ArrayList<RemappingEntry>>(ProtocolVersion.class);
	{
		for (ProtocolVersion version : ProtocolVersion.values()) {
			entries.put(version, new ArrayList<RemappingEntry>());
		}
	}

	@SuppressWarnings("deprecation")
	SpecificType(EType etype, EntityType type, RemappingEntriesForProtocols... entries) {
		this(etype, type.getTypeId(), entries);
	}

	SpecificType(EType etype, int typeId, RemappingEntriesForProtocols... entries) {
		this.etype = etype;
		this.typeId = typeId;
		for (RemappingEntriesForProtocols rp : entries) {
			for (ProtocolVersion version : rp.versions) {
				this.entries.get(version).addAll(Arrays.asList(rp.entries));
			}
		}
	}

	@SuppressWarnings("deprecation")
	SpecificType(EType etype, EntityType type, SpecificType superType, RemappingEntriesForProtocols... entries) {
		this(etype, type.getTypeId(), superType, entries);
	}

	SpecificType(EType etype, int typeId, SpecificType superType, RemappingEntriesForProtocols... entries) {
		this.etype = etype;
		this.typeId = typeId;
		for (Entry<ProtocolVersion, ArrayList<RemappingEntry>> entry : superType.entries.entrySet()) {
			this.entries.get(entry.getKey()).addAll(entry.getValue());
		}
		for (RemappingEntriesForProtocols rp : entries) {
			for (ProtocolVersion version : rp.versions) {
				this.entries.get(version).addAll(Arrays.asList(rp.entries));
			}
		}
	}

	public List<RemappingEntry> getRemaps(ProtocolVersion version) {
		return entries.get(version);
	}

	private enum EType {
		NONE, OBJECT, MOB
	}

	private static class RemappingEntriesForProtocols {
		private ArrayList<ProtocolVersion> versions = new ArrayList<ProtocolVersion>();
		private RemappingEntry[] entries;
		protected RemappingEntriesForProtocols(RemappingEntry... entries) {
			this.entries = entries;
		}
		protected RemappingEntriesForProtocols addProtocols(ProtocolVersion... versions) {
			this.versions.addAll(Arrays.asList(versions));
			return this;
		}
	}

}
