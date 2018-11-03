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
import org.bukkit.block.data.Rotatable;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PlayerHeadToLegacyOwnerComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.DragonHeadToDragonPlayerHeadComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTInt;
import protocolsupport.protocol.utils.types.nbt.NBTByte;
import protocolsupport.protocol.utils.types.nbt.NBTNumber;
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

	protected static final EnumMap<ProtocolVersion, TileNBTRemapper> tileRemappers = new EnumMap<>(ProtocolVersion.class);
	static {
		for (ProtocolVersion version : ProtocolVersion.getAllSupported()) {
			tileRemappers.put(version, new TileNBTRemapper());
		}
	}
	public static TileNBTRemapper getRemapper(ProtocolVersion version) {
		return tileRemappers.get(version);
	}

	protected static void register(TileEntityType type, Function<NBTCompound, NBTCompound> transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			tileRemappers.get(version).tile2tile.computeIfAbsent(type, k -> new ArrayList<>()).add(transformer);
		}
	}

	protected static void registerLegacy(List<Material> types, BiFunction<BlockData, NBTCompound, NBTCompound> transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			Map<Integer, List<BiFunction<BlockData, NBTCompound, NBTCompound>>> map = tileRemappers.get(version).tilestate2tile;
			types.forEach(type -> {
				MaterialAPI.getBlockDataList(type).stream()
				.map(MaterialAPI::getBlockDataNetworkId)
				.forEach(i -> {
					map.computeIfAbsent(i, k -> new ArrayList<>()).add(transformer);
				});
			});
		}
	}

	protected static void registerLegacyState(List<Material> types, BiFunction<BlockData, NBTCompound, NBTCompound> transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			Map<Integer, BiFunction<BlockData, NBTCompound, NBTCompound>> map = tileRemappers.get(version).state2tile;
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
		List<Material> skulls = Arrays.asList(
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
		);
		registerLegacy(
			skulls, (blockdata, input) -> {
				byte skulltype = 0;
				switch(blockdata.getMaterial()) {
					case SKELETON_SKULL: case SKELETON_WALL_SKULL: skulltype = 0; break;
					case WITHER_SKELETON_SKULL: case WITHER_SKELETON_WALL_SKULL: skulltype = 1; break;
					case ZOMBIE_HEAD: case ZOMBIE_WALL_HEAD: skulltype = 2; break;
					case PLAYER_HEAD: case PLAYER_WALL_HEAD: skulltype = 3; break;
					case CREEPER_HEAD: case CREEPER_WALL_HEAD: skulltype = 4; break;
					case DRAGON_HEAD: case DRAGON_WALL_HEAD: skulltype = 5; break;
					default: break;
				}
				input.setTag("SkullType", new NBTByte(skulltype));
				if (blockdata instanceof Rotatable) {
					System.out.println("GOIND FOR ORIENTATION REMAP!");
					Rotatable rotatable = (Rotatable) blockdata;
					byte rotation = 0;
					switch (rotatable.getRotation()) {
						case SOUTH: rotation = 0; break;
						case SOUTH_SOUTH_WEST: rotation = 1; break;
						case SOUTH_WEST: rotation = 2; break;
						case WEST_SOUTH_WEST: rotation = 3; break;
						case WEST: rotation = 4; break;
						case WEST_NORTH_WEST: rotation = 5; break;
						case NORTH_WEST: rotation = 6; break;
						case NORTH_NORTH_WEST: rotation = 7; break;
						case NORTH: rotation = 8; break;
						case NORTH_NORTH_EAST: rotation = 9; break;
						case NORTH_EAST: rotation = 10; break;
						case EAST_NORTH_EAST: rotation = 11; break;
						case EAST: rotation = 12; break;
						case EAST_SOUTH_EAST: rotation = 13; break;
						case SOUTH_EAST: rotation = 14; break;
						case SOUTH_SOUTH_EAST: rotation = 15; break;
						default: break;
					}
					input.setTag("Rot", new NBTByte(rotation));
				}
				return input;
			},
			ProtocolVersionsHelper.BEFORE_1_13
		);
		registerLegacy(
			skulls, (blockdata, input) -> {
				NBTNumber skulltype = input.getNumberTag("SkullType");
				if (skulltype != null && skulltype.getAsInt() == 5) {
					input.setTag("SkullType", new NBTByte((byte) 3));
					input.setTag("Owner", DragonHeadToDragonPlayerHeadComplexRemapper.createTag());
				}
				return input;
			},
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_8)
		);
		registerLegacy(
			skulls,	(blockdata, input) -> {
				PlayerHeadToLegacyOwnerComplexRemapper.remap(input, "Owner", "ExtraType");
				return input;
			},
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_7_5)
		);
		registerLegacyState(
			Arrays.asList(
				Material.BLACK_BED,
				Material.RED_BED,
				Material.GREEN_BED,
				Material.BROWN_BED,
				Material.BLUE_BED,
				Material.PURPLE_BED,
				Material.CYAN_BED,
				Material.LIGHT_GRAY_BED,
				Material.GRAY_BED,
				Material.PINK_BED,
				Material.LIME_BED,
				Material.YELLOW_BED,
				Material.LIGHT_BLUE_BED,
				Material.MAGENTA_BED,
				Material.ORANGE_BED,
				Material.WHITE_BED
			), (blockdata, input) -> {
				int color = -1;
				switch (blockdata.getMaterial()) {
					case WHITE_BED: color = 0; break;
					case ORANGE_BED: color = 1; break;
					case MAGENTA_BED: color = 2; break;
					case LIGHT_BLUE_BED: color = 3; break;
					case YELLOW_BED: color = 4; break;
					case LIME_BED: color = 5; break;
					case PINK_BED: color = 6; break;
					case GRAY_BED: color = 7; break;
					case LIGHT_GRAY_BED: color = 8; break;
					case CYAN_BED: color = 9; break;
					case PURPLE_BED: color = 10; break;
					case BLUE_BED: color = 11; break;
					case BROWN_BED: color = 12; break;
					case GREEN_BED: color = 13; break;
					case RED_BED: color = 14; break;
					case BLACK_BED: color = 15; break;
					default: break;
				}
				setTileType(input, TileEntityType.BED);
				input.setTag("color", new NBTInt(color));
				return input;
			}, ProtocolVersionsHelper.ALL_1_12
		);
	}



	public static void setTileType(NBTCompound tag, TileEntityType type) {
		tag.setTag(tileEntityTypeKey, new NBTString(type.getRegistryId()));
	}

	public static String getTileType(NBTCompound tag) {
		return NBTString.getValueOrNull(tag.getTagOfType(tileEntityTypeKey, NBTType.STRING));
	}

	public static void setPosition(NBTCompound tag, Position position) {
		tag.setTag("x", new NBTInt(position.getX()));
		tag.setTag("y", new NBTInt(position.getY()));
		tag.setTag("z", new NBTInt(position.getZ()));
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


	private final Map<TileEntityType, List<Function<NBTCompound, NBTCompound>>> tile2tile = new EnumMap<>(TileEntityType.class);
	private final Map<Integer, List<BiFunction<BlockData, NBTCompound, NBTCompound>>> tilestate2tile = new Int2ObjectOpenHashMap<>();
	private final Map<Integer, BiFunction<BlockData, NBTCompound, NBTCompound>> state2tile = new Int2ObjectOpenHashMap<>();
	
	public NBTCompound remap(NBTCompound compound, int blockstate) {
		List<Function<NBTCompound, NBTCompound>> transformers = tile2tile.get(TileEntityType.getByRegistryId(getTileType(compound)));
		if (transformers != null) {
			for (Function<NBTCompound, NBTCompound> transformer : transformers) {
				compound = transformer.apply(compound);
			}
		}
		if (blockstate != -1) {
			List<BiFunction<BlockData, NBTCompound, NBTCompound>> legacyTransformers = tilestate2tile.get(blockstate);
			if (transformers != null) {
				for (BiFunction<BlockData, NBTCompound, NBTCompound> legacyTransformer : legacyTransformers) {
					compound = legacyTransformer.apply(MaterialAPI.getBlockDataByNetworkId(blockstate), compound);
				}
			}
		}
		return compound;
	}

	public NBTCompound getLegacyTileFromBlock(Position position, int blockstate) {
		BiFunction<BlockData, NBTCompound, NBTCompound> constructor = state2tile.get(blockstate);
		if (constructor != null) {
			BlockData blockdata = MaterialAPI.getBlockDataByNetworkId(blockstate);
			NBTCompound newTile = new NBTCompound();
			setPosition(newTile, position);
			return constructor.apply(blockdata, newTile);
		}
		return null;
	}

	public boolean tileThatNeedsBlockstate(int blockstate) {
		return tilestate2tile.keySet().contains(blockstate);
	}

	public boolean usedToBeTile(int blockstate) {
		return state2tile.keySet().contains(blockstate);
	}

}
