package protocolsupport.protocol.typeremapper.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.DragonHeadToDragonPlayerHeadComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PlayerHeadToLegacyOwnerComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntity;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.nbt.NBTByte;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTInt;
import protocolsupport.protocol.utils.types.nbt.NBTNumber;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;

public class TileNBTRemapper {

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

	protected static void register(TileEntityType type, Consumer<TileEntity> transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			tileRemappers.get(version).tileToTile
			.computeIfAbsent(type, k -> new ArrayList<>())
			.add(transformer);
		}
	}

	protected static void registerLegacy(List<Material> types, BiConsumer<BlockData, TileEntity> transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			Int2ObjectMap<List<BiConsumer<BlockData, TileEntity>>> map = tileRemappers.get(version).tileWithBlockdataToTile;
			types.forEach(type ->
				MaterialAPI.getBlockDataList(type).stream()
				.mapToInt(MaterialAPI::getBlockDataNetworkId)
				.forEach(i -> map.computeIfAbsent(i, k -> new ArrayList<>()).add(transformer))
			);
		}
	}

	protected static void registerLegacyState(List<Material> types, BiFunction<Position, BlockData, TileEntity> transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			Int2ObjectMap<BiFunction<Position, BlockData, TileEntity>> map = tileRemappers.get(version).blockdataToTile;
			types.forEach(type ->
				MaterialAPI.getBlockDataList(type).stream()
				.mapToInt(MaterialAPI::getBlockDataNetworkId)
				.forEach(i -> map.put(i, transformer))
			);
		}
	}

	static {
		for (TileEntityType type : TileEntityType.values()) {
			register(
				type,
				tile -> tile.getNBT().setTag("id", new NBTString(newToOldType.getOrDefault(tile.getType(), tile.getType().getRegistryId()))),
				ProtocolVersionsHelper.BEFORE_1_11
			);
		}
		register(
			TileEntityType.MOB_SPAWNER,
			tile -> {
				NBTCompound nbt = tile.getNBT();
				if (nbt.getTagOfType("SpawnData", NBTType.COMPOUND) == null) {
					NBTCompound spawndata = new NBTCompound();
					spawndata.setTag("id", new NBTString(NetworkEntityType.PIG.getKey()));
					nbt.setTag("SpawnData", spawndata);
				}
			},
			ProtocolVersionsHelper.ALL_PC
		);
		register(
			TileEntityType.MOB_SPAWNER,
			tile -> {
				NBTCompound spawndata = tile.getNBT().getTagOfType("SpawnData", NBTType.COMPOUND);
				if (spawndata != null) {
					NetworkEntityType type = NetworkEntityType.getByRegistrySTypeId(NBTString.getValueOrNull(spawndata.getTagOfType("id", NBTType.STRING)));
					if (type != NetworkEntityType.NONE) {
						spawndata.setTag("id", new NBTString(LegacyEntityId.getLegacyName(type)));
					}
				}
			},
			ProtocolVersionsHelper.BEFORE_1_11
		);
		register(
			TileEntityType.MOB_SPAWNER,
			tile -> {
				NBTCompound nbt = tile.getNBT();
				if (nbt.removeTag("SpawnData") != null) {
					nbt.removeTag("SpawnPotentials");
				}
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
			skulls, (blockdata, tile) -> {
				NBTCompound nbt = tile.getNBT();
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
				nbt.setTag("SkullType", new NBTByte(skulltype));
				if (blockdata instanceof Rotatable) {
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
					nbt.setTag("Rot", new NBTByte(rotation));
				}
			},
			ProtocolVersionsHelper.BEFORE_1_13
		);
		registerLegacy(
			skulls, (blockdata, tile) -> {
				NBTCompound nbt = tile.getNBT();
				NBTNumber skulltype = nbt.getNumberTag("SkullType");
				if ((skulltype != null) && (skulltype.getAsInt() == 5)) {
					nbt.setTag("SkullType", new NBTByte((byte) 3));
					nbt.setTag("Owner", DragonHeadToDragonPlayerHeadComplexRemapper.createTag());
				}
			},
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_8)
		);
		registerLegacy(
			skulls,	(blockdata, tile) -> PlayerHeadToLegacyOwnerComplexRemapper.remap(tile.getNBT(), "Owner", "ExtraType"),
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
			), (position, blockdata) -> {
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
				NBTCompound tag = new NBTCompound();
				tag.setTag("color", new NBTInt(color));
				return new TileEntity(TileEntityType.BED, position, tag);
			}, ProtocolVersionsHelper.ALL_1_12
		);
	}

	public static String[] getSignLines(NBTCompound tag) {
		String[] lines = new String[4];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = NBTString.getValueOrDefault(tag.getTagOfType("Text" + (i + 1), NBTType.STRING), "");
		}
		return lines;
	}


	protected final Map<TileEntityType, List<Consumer<TileEntity>>> tileToTile = new EnumMap<>(TileEntityType.class);
	protected final Int2ObjectMap<List<BiConsumer<BlockData, TileEntity>>> tileWithBlockdataToTile = new Int2ObjectOpenHashMap<>();
	protected final Int2ObjectMap<BiFunction<Position, BlockData, TileEntity>> blockdataToTile = new Int2ObjectOpenHashMap<>();

	public TileEntity remap(TileEntity tileentity, int blockdata) {
		List<Consumer<TileEntity>> transformers = tileToTile.get(tileentity.getType());
		if (transformers != null) {
			for (Consumer<TileEntity> transformer : transformers) {
				transformer.accept(tileentity);
			}
		}
		if (blockdata != -1) {
			List<BiConsumer<BlockData, TileEntity>> legacyTransformers = tileWithBlockdataToTile.get(blockdata);
			if (legacyTransformers != null) {
				for (BiConsumer<BlockData, TileEntity> legacyTransformer : legacyTransformers) {
					legacyTransformer.accept(MaterialAPI.getBlockDataByNetworkId(blockdata), tileentity);
				}
			}
		}
		return tileentity;
	}

	public TileEntity getLegacyTileFromBlock(Position position, int blockdataId) {
		BiFunction<Position, BlockData, TileEntity> constructor = blockdataToTile.get(blockdataId);
		if (constructor != null) {
			return constructor.apply(position, MaterialAPI.getBlockDataByNetworkId(blockdataId));
		}
		return null;
	}

	public boolean tileThatNeedsBlockData(int blockstate) {
		return tileWithBlockdataToTile.containsKey(blockstate);
	}

	public boolean usedToBeTile(int blockstate) {
		return blockdataToTile.containsKey(blockstate);
	}

}
