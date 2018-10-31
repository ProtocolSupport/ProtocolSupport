package protocolsupport.protocol.typeremapper.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;


import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
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

	protected static final EnumMap<ProtocolVersion, EnumMap<TileEntityType, List<Function<NBTCompound, NBTCompound>>>> tile2tile = new EnumMap<>(ProtocolVersion.class);
	protected static final EnumMap<ProtocolVersion, Map<Integer, List<BiFunction<BlockData, NBTCompound, NBTCompound>>>> tilestate2tile = new EnumMap<>(ProtocolVersion.class);
	protected static final EnumMap<ProtocolVersion, Map<Integer, Function<BlockData, NBTCompound>>> state2tile = new EnumMap<>(ProtocolVersion.class);

	protected static void register(TileEntityType type, Function<NBTCompound, NBTCompound> transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			EnumMap<TileEntityType, List<Function<NBTCompound, NBTCompound>>> map = tile2tile.computeIfAbsent(version, k -> new EnumMap<>(TileEntityType.class));
			map.computeIfAbsent(type, k -> new ArrayList<>()).add(transformer);
		}
	}

	protected static void registerLegacy(List<Material> types, BiFunction<BlockData, NBTCompound, NBTCompound> transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			Map<Integer, List<BiFunction<BlockData, NBTCompound, NBTCompound>>> map = tilestate2tile.computeIfAbsent(version, k -> new Int2ObjectOpenHashMap<>());
			types.forEach(type -> {
				MaterialAPI.getBlockDataList(type).stream()
				.map(MaterialAPI::getBlockDataNetworkId)
				.forEach(i -> {
					map.computeIfAbsent(i, k -> new ArrayList<>()).add(transformer);
				});
			});
		}
	}

	protected static void registerLegacy(List<Material> types, Function<BlockData, NBTCompound> transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			Map<Integer, Function<BlockData, NBTCompound>> map = state2tile.computeIfAbsent(version, k -> new Int2ObjectOpenHashMap<>());
			types.forEach(type -> {
				MaterialAPI.getBlockDataList(type).stream()
				.map(MaterialAPI::getBlockDataNetworkId)
				.forEach(i -> {
					map.put(i, transformer);
				});
			});
		}
	}

	static {
		for (TileEntityType type : TileEntityType.values()) {
			register(
				type,
				(input) -> {
					input.setTag(tileEntityTypeKey, new NBTString(newToOldType.getOrDefault(type, type.getRegistryId())));
					return input;
				},
				ProtocolVersionsHelper.BEFORE_1_11
			);
		}
		register(
			TileEntityType.MOB_SPAWNER,
			(input) -> {
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
			(input) -> {
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
			(input) -> {
				if (input.getTagOfType("SpawnData", NBTType.COMPOUND) != null) {
					input.removeTag("SpawnPotentials");
					input.removeTag("SpawnData");
				}
				return input;
			},
			ProtocolVersionsHelper.BEFORE_1_9
		);
		registerLegacy(
			Arrays.asList(
					Material.SKELETON_SKULL, 
					Material.WITHER_SKELETON_SKULL, 
					Material.CREEPER_HEAD, 
					Material.DRAGON_HEAD, 
					Material.PLAYER_HEAD, 
					Material.ZOMBIE_HEAD,
					Material.SKELETON_WALL_SKULL, 
					Material.WITHER_SKELETON_WALL_SKULL, 
					Material.CREEPER_WALL_HEAD, 
					Material.DRAGON_WALL_HEAD, 
					Material.PLAYER_WALL_HEAD, 
					Material.ZOMBIE_WALL_HEAD
			), (blockdata, input) -> {
				if (blockdata instanceof Rotatable) {
					System.out.println("ON GROUND WooooHOHOOOO");
				} else if (blockdata instanceof Directional) {
					System.out.println("ON WALL WooooHOHOOOO");
				}
				return input;
			},
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_8)
		);
		register(
			TileEntityType.SKULL,
			(input) -> {
				PlayerHeadToLegacyOwnerComplexRemapper.remap(input, "Owner", "ExtraType");
				return input;
			},
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_7_5)
		);
		registerLegacy(
			Arrays.asList(
				Material.BLACK_BED, 
				Material.BLUE_BED, 
				Material.BROWN_BED, 
				Material.CYAN_BED, 
				Material.GRAY_BED, 
				Material.GREEN_BED, 
				Material.LIGHT_BLUE_BED, 
				Material.LIGHT_GRAY_BED,
				Material.LIME_BED, 
				Material.MAGENTA_BED, 
				Material.ORANGE_BED, 
				Material.PINK_BED, 
				Material.PURPLE_BED, 
				Material.RED_BED, 
				Material.WHITE_BED, 
				Material.YELLOW_BED
			), (blockdata) -> {
				return new NBTCompound();
			}, ProtocolVersionsHelper.RANGE__1_11_1__1_12_2
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

	private ConnectionImpl connection;

	public TileNBTRemapper(ConnectionImpl connection) {
		this.connection = connection;
	}

	public NBTCompound remap(NBTCompound compound) {
		EnumMap<TileEntityType, List<Function<NBTCompound, NBTCompound>>> map = tile2tile.get(connection.getVersion());
		if (map != null) {
			List<Function<NBTCompound, NBTCompound>> transformers = map.get(TileEntityType.getByRegistryId(getTileType(compound)));
			if (transformers != null) {
				for (Function<NBTCompound, NBTCompound> transformer : transformers) {
					compound = transformer.apply(compound);
				}
			}
		}
		int tileblock = connection.getCache().getTileBlockDataCache().getTileBlockData(getPosition(compound));
		if (tileblock != -1) {
			//If the block is cached then there is also a transformer responsible for that caching.
			for (BiFunction<BlockData, NBTCompound, NBTCompound> transformer : tilestate2tile.get(connection.getVersion()).get(tileblock)) {
				compound = transformer.apply(MaterialAPI.getBlockDataByNetworkId(tileblock), compound);
			}
		}
		return compound;
	}

	public NBTCompound getLegacyTileFromBlock(int blockstate) {
		Map<Integer, Function<BlockData, NBTCompound>> map = state2tile.get(connection.getVersion());
		if (map != null) {
			Function<BlockData, NBTCompound> constructor = map.get(blockstate);
			if (constructor != null) {
				BlockData blockdata = MaterialAPI.getBlockDataByNetworkId(blockstate);
				return constructor.apply(blockdata);
			}
		}
		return null;
	}

	public boolean isToBeCachedTile(int blockstate) {
		return tilestate2tile.get(connection.getVersion()).keySet().contains(blockstate);
	}

	public boolean usedToBeTile(ProtocolVersion version, int blockstate) {
		return state2tile.get(connection.getVersion()).keySet().contains(blockstate);
	}

}
