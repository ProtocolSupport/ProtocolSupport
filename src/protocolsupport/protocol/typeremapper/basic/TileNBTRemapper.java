package protocolsupport.protocol.typeremapper.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BiFunction;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PlayerHeadToLegacyOwnerComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class TileNBTRemapper {

	protected static final String tileEntityTypeKey = "id";

	protected static final EnumMap<TileEntityType, String> newToOldType = new EnumMap<>(TileEntityType.class);
	static {
		newToOldType.put(TileEntityType.MOB_SPAWNER, "MobSpawner");
		newToOldType.put(TileEntityType.COMMAND_BLOCK, "Control");
		newToOldType.put(TileEntityType.BEACON, "Beacon");
		newToOldType.put(TileEntityType.SKULL, "Skull");
		newToOldType.put(TileEntityType.BANNER, "Banner");
		newToOldType.put(TileEntityType.STRUCTURE, "Structure");
		newToOldType.put(TileEntityType.END_GATEWAY, "Airportal");
		newToOldType.put(TileEntityType.SIGN, "Sign");
	}

	//TODO make version a main key, so remapping table can be extracted by middle packets
	protected static final EnumMap<TileEntityType, EnumMap<ProtocolVersion, List<BiFunction<ConnectionImpl, NBTCompound, NBTCompound>>>> registry = new EnumMap<>(TileEntityType.class);

	protected static void register(TileEntityType type, BiFunction<ConnectionImpl, NBTCompound, NBTCompound> transformer, ProtocolVersion... versions) {
		EnumMap<ProtocolVersion, List<BiFunction<ConnectionImpl, NBTCompound, NBTCompound>>> map = registry.computeIfAbsent(type, k -> new EnumMap<>(ProtocolVersion.class));
		for (ProtocolVersion version : versions) {
			map.computeIfAbsent(version, k -> new ArrayList<>()).add(transformer);
		}
	}

	static {
		for (TileEntityType type : TileEntityType.values()) {
			register(
				type,
				(connection, input) -> {
					input.setTag(tileEntityTypeKey, new NBTString(newToOldType.getOrDefault(type, type.getRegistryId())));
					return input;
				},
				ProtocolVersionsHelper.BEFORE_1_11
			);
		}
		register(
			TileEntityType.MOB_SPAWNER,
			(connection, input) -> {
				if (input.getTagOfType("SpawnData", NBTType.COMPOUND) == null) {
					NBTCompound spawndata = new NBTCompound();
					spawndata.setTag("id", new NBTString(NetworkEntityType.PIG.getKey()));
					input.setTag("SpawnData", spawndata);
				}
				return input;
			},
			ProtocolVersionsHelper.ALL_PC
		);
		register(
			TileEntityType.MOB_SPAWNER,
			(connection, input) -> {
				NBTCompound spawndata = input.getTagOfType("SpawnData", NBTType.COMPOUND);
				if (spawndata != null) {
					NetworkEntityType type = NetworkEntityType.getByRegistrySTypeId(NBTString.getValueOrNull(spawndata.getTagOfType("id", NBTType.STRING)));
					if (type != NetworkEntityType.NONE) {
						spawndata.setTag("id", new NBTString(LegacyEntityId.getLegacyName(type)));
					}
				}
				return input;
			},
			ProtocolVersionsHelper.BEFORE_1_11
		);
		register(
			TileEntityType.MOB_SPAWNER,
			(connection, input) -> {
				if (input.getTagOfType("SpawnData", NBTType.COMPOUND) != null) {
					input.removeTag("SpawnPotentials");
					input.removeTag("SpawnData");
				}
				return input;
			},
			ProtocolVersionsHelper.BEFORE_1_9
		);
		register(
			TileEntityType.SKULL,
			(connection, input) -> {
				BlockData skulldata = getTileDataFromCache(input, connection);
				if (skulldata instanceof Rotatable) {
					System.out.println("ON GROUND WooooHOHOOOO");
				} else if (skulldata instanceof Directional) {
					System.out.println("ON WALL WooooHOHOOOO");
				}
				return input;
			},
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_8)
		);
		register(
			TileEntityType.SKULL,
			(connection, input) -> {
				PlayerHeadToLegacyOwnerComplexRemapper.remap(input, "Owner", "ExtraType");
				return input;
			},
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_7_5)
		);
	}

	public static String getTileType(NBTCompound tag) {
		return NBTString.getValueOrNull(tag.getTagOfType(tileEntityTypeKey, NBTType.STRING));
	}

	public static Position getPosition(NBTCompound tag) {
		return new Position(tag.getNumberTag("x").getAsInt(), tag.getNumberTag("y").getAsInt(), tag.getNumberTag("z").getAsInt());
	}

	public static String[] getSignLines(NBTCompound tag) {
		String[] lines = new String[4];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = NBTString.getValueOrDefault(tag.getTagOfType("Text" + (i + 1), NBTType.STRING), "");
		}
		return lines;
	}

	private static BlockData getTileDataFromCache(NBTCompound input, ConnectionImpl connection) {
		return connection.getCache().getTileBlockDataCache().getTileBlockData(getPosition(input));
	}

	public static NBTCompound remap(ConnectionImpl connection, NBTCompound compound) {
		EnumMap<ProtocolVersion, List<BiFunction<ConnectionImpl, NBTCompound, NBTCompound>>> map = registry.get(TileEntityType.getByRegistryId(getTileType(compound)));
		if (map != null) {
			List<BiFunction<ConnectionImpl, NBTCompound, NBTCompound>> transformers = map.get(connection.getVersion());
			if (transformers != null) {
				for (BiFunction<ConnectionImpl, NBTCompound, NBTCompound> transformer : transformers) {
					compound = transformer.apply(connection, compound);
				}
				return compound;
			}
		}
		return compound;
	}

	private static final IntArrayList blockdataCacheTileStates = new IntArrayList();
	static {
		Arrays.asList(Material.SKELETON_SKULL, Material.WITHER_SKELETON_SKULL, Material.CREEPER_HEAD, Material.DRAGON_HEAD, Material.PLAYER_HEAD, Material.ZOMBIE_HEAD,
				Material.SKELETON_WALL_SKULL, Material.WITHER_SKELETON_WALL_SKULL, Material.CREEPER_WALL_HEAD, Material.DRAGON_WALL_HEAD, Material.PLAYER_WALL_HEAD, Material.ZOMBIE_WALL_HEAD,
				Material.BLACK_BED, Material.BLUE_BED, Material.BROWN_BED, Material.CYAN_BED, Material.GRAY_BED, Material.GREEN_BED, Material.LIGHT_BLUE_BED, Material.LIGHT_GRAY_BED,
				Material.LIME_BED, Material.MAGENTA_BED, Material.ORANGE_BED, Material.PINK_BED, Material.PURPLE_BED, Material.RED_BED, Material.WHITE_BED, Material.YELLOW_BED)
		.forEach(mat -> MaterialAPI.getBlockDataList(mat).forEach(data -> blockdataCacheTileStates.add(MaterialAPI.getBlockDataNetworkId(data))));
	}
	public static boolean shouldCacheBlockState(int blockstate) {
		return blockdataCacheTileStates.contains(blockstate);
	}

}
