package protocolsupport.protocol.typeremapper.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.DragonHeadToDragonPlayerHeadComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PlayerHeadToLegacyOwnerComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
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
			.add((tile, blockdata) -> transformer.accept(tile));
		}
	}

	protected static void register(TileEntityType type, TileWithBlockDataRemapper transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			TileNBTRemapper remapper = tileRemappers.get(version);
			remapper.tileNeedsBlockData.addAll(transformer.remappers.keySet());
			remapper.tileToTile
			.computeIfAbsent(type, k -> new ArrayList<>())
			.add(transformer);
		}
	}

	protected static class TileWithBlockDataRemapper implements ObjIntConsumer<TileEntity> {
		protected final Int2ObjectMap<Consumer<NBTCompound>> remappers = new Int2ObjectOpenHashMap<>();
		@Override
		public void accept(TileEntity tile, int blockdata) {
			Consumer<NBTCompound> remapper = remappers.get(blockdata);
			if (remapper != null) {
				remapper.accept(tile.getNBT());
			}
		}
	}

	protected static void registerLegacyState(Material material, Function<Position, TileEntity> transformer, ProtocolVersion... versions) {
		MaterialAPI.getBlockDataList(material).forEach(blockdata -> registerLegacyState(blockdata, transformer, versions));
	}

	protected static void registerLegacyState(BlockData blockdata, Function<Position, TileEntity> transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			tileRemappers.get(version).blockdataToTile.put(MaterialAPI.getBlockDataNetworkId(blockdata), transformer);
		}
	}

	protected static class BedTileSupplier implements Function<Position, TileEntity> {

		protected final int color;
		public BedTileSupplier(int color) {
			this.color = color;
		}

		@Override
		public TileEntity apply(Position position) {
			NBTCompound tag = new NBTCompound();
			tag.setTag("color", new NBTInt(color));
			return new TileEntity(TileEntityType.BED, position, tag);
		}

	}

	static {
		for (TileEntityType type : TileEntityType.values()) {
			register(
				type,
				tile -> tile.getNBT().setTag("id", new NBTString(newToOldType.getOrDefault(tile.getType(), tile.getType().getRegistryId()))),
				ProtocolVersionsHelper.BEFORE_1_11
			);
			register(
				type,
				(version, input) -> {
					input.setTag(tileEntityTypeKey, new NBTString(peTypes.getOrDefault(type, type.getRegistryId())));
					return input;
				},
				ProtocolVersion.MINECRAFT_PE
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
		register(
			TileEntityType.BANNER, new TileWithBlockDataRemapper() {
				{
					register(Material.WHITE_BANNER, 15);
					register(Material.ORANGE_BANNER, 14);
					register(Material.MAGENTA_BANNER, 13);
					register(Material.LIGHT_BLUE_BANNER, 12);
					register(Material.YELLOW_BANNER, 11);
					register(Material.LIME_BANNER, 10);
					register(Material.PINK_BANNER, 9);
					register(Material.GRAY_BANNER, 8);
					register(Material.LIGHT_GRAY_BANNER, 7);
					register(Material.CYAN_BANNER, 6);
					register(Material.PURPLE_BANNER, 5);
					register(Material.BLUE_BANNER, 4);
					register(Material.BROWN_BANNER, 3);
					register(Material.GREEN_BANNER, 2);
					register(Material.RED_BANNER, 1);
					register(Material.BLACK_BANNER, 0);
					register(Material.WHITE_WALL_BANNER, 15);
					register(Material.ORANGE_WALL_BANNER, 14);
					register(Material.MAGENTA_WALL_BANNER, 13);
					register(Material.LIGHT_BLUE_WALL_BANNER, 12);
					register(Material.YELLOW_WALL_BANNER, 11);
					register(Material.LIME_WALL_BANNER, 10);
					register(Material.PINK_WALL_BANNER, 9);
					register(Material.GRAY_WALL_BANNER, 8);
					register(Material.LIGHT_GRAY_WALL_BANNER, 7);
					register(Material.CYAN_WALL_BANNER, 6);
					register(Material.PURPLE_WALL_BANNER, 5);
					register(Material.BLUE_WALL_BANNER, 4);
					register(Material.BROWN_WALL_BANNER, 3);
					register(Material.GREEN_WALL_BANNER, 2);
					register(Material.RED_WALL_BANNER, 1);
					register(Material.BLACK_WALL_BANNER, 0);
				}
				void register(Material banner, int color) {
					for (BlockData blockdata : MaterialAPI.getBlockDataList(banner)) {
						remappers.put(MaterialAPI.getBlockDataNetworkId(blockdata), nbt -> nbt.setTag("Base", new NBTInt(color)));
					}
				}
			},
			ProtocolVersionsHelper.BEFORE_1_13
		);
		register(
			TileEntityType.SKULL, new TileWithBlockDataRemapper() {
				{
					register(Material.SKELETON_SKULL, 0);
					register(Material.WITHER_SKELETON_SKULL, 1);
					register(Material.ZOMBIE_HEAD, 2);
					register(Material.PLAYER_HEAD, 3);
					register(Material.CREEPER_HEAD, 4);
					register(Material.DRAGON_HEAD, 5);
					register(Material.SKELETON_WALL_SKULL, 0);
					register(Material.WITHER_SKELETON_WALL_SKULL, 1);
					register(Material.ZOMBIE_WALL_HEAD, 2);
					register(Material.PLAYER_WALL_HEAD, 3);
					register(Material.CREEPER_WALL_HEAD, 4);
					register(Material.DRAGON_WALL_HEAD, 5);
				}
				void register(Material skull, int skulltype) {
					for (BlockData blockdata : MaterialAPI.getBlockDataList(skull)) {
						byte rotation = 0;
						if (blockdata instanceof Rotatable) {
							Rotatable rotatable = (Rotatable) blockdata;
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
						}
						byte rotationF = rotation;
						remappers.put(MaterialAPI.getBlockDataNetworkId(blockdata), nbt -> {
							nbt.setTag("SkullType", new NBTByte((byte) skulltype));
							nbt.setTag("Rot", new NBTByte(rotationF));
						});
					}
				}
			},
			ProtocolVersionsHelper.BEFORE_1_13
		);
		register(
			TileEntityType.SKULL,
			tile -> {
				NBTCompound nbt = tile.getNBT();
				NBTNumber skulltype = nbt.getNumberTag("SkullType");
				if ((skulltype != null) && (skulltype.getAsInt() == 5)) {
					nbt.setTag("SkullType", new NBTByte((byte) 3));
					nbt.setTag("Owner", DragonHeadToDragonPlayerHeadComplexRemapper.createTag());
				}
			},
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_8)
		);
		register(
			TileEntityType.SKULL,
			tile -> PlayerHeadToLegacyOwnerComplexRemapper.remap(tile.getNBT(), "Owner", "ExtraType"),
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_7_5)
		);

		registerLegacyState(Material.WHITE_BED, new BedTileSupplier(0), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.ORANGE_BED, new BedTileSupplier(1), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.MAGENTA_BED, new BedTileSupplier(2), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.LIGHT_BLUE_BED, new BedTileSupplier(3), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.YELLOW_BED, new BedTileSupplier(4), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.LIME_BED, new BedTileSupplier(5), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.PINK_BED, new BedTileSupplier(6), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.GRAY_BED, new BedTileSupplier(7), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.LIGHT_GRAY_BED, new BedTileSupplier(8), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.CYAN_BED, new BedTileSupplier(9), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.PURPLE_BED, new BedTileSupplier(10), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.BLUE_BED, new BedTileSupplier(11), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.BROWN_BED, new BedTileSupplier(12), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.GREEN_BED, new BedTileSupplier(13), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.RED_BED, new BedTileSupplier(14), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.BLACK_BED, new BedTileSupplier(15), ProtocolVersionsHelper.ALL_1_12);
	}

	public static String[] getSignLines(NBTCompound tag) {
		String[] lines = new String[4];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = NBTString.getValueOrDefault(tag.getTagOfType("Text" + (i + 1), NBTType.STRING), "");
		}
		return lines;
	}


	protected final Map<TileEntityType, List<ObjIntConsumer<TileEntity>>> tileToTile = new EnumMap<>(TileEntityType.class);
	protected final IntSet tileNeedsBlockData = new IntOpenHashSet();

	protected final Int2ObjectMap<Function<Position, TileEntity>> blockdataToTile = new Int2ObjectOpenHashMap<>();

	public TileEntity remap(TileEntity tileentity, int blockdata) {
		List<ObjIntConsumer<TileEntity>> transformers = tileToTile.get(tileentity.getType());
		if (transformers != null) {
			for (ObjIntConsumer<TileEntity> transformer : transformers) {
				transformer.accept(tileentity, blockdata);
			}
		}
		return tileentity;
	}

	public TileEntity getLegacyTileFromBlock(Position position, int blockdata) {
		Function<Position, TileEntity> constructor = blockdataToTile.get(blockdata);
		if (constructor != null) {
			return constructor.apply(position);
		}
		return null;
	}

	public boolean tileThatNeedsBlockData(int blockdata) {
		return tileNeedsBlockData.contains(blockdata);
	}

	public boolean usedToBeTile(int blockdata) {
		return blockdataToTile.containsKey(blockdata);
	}

}
