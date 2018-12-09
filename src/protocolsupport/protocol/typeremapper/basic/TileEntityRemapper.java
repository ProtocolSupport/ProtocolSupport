package protocolsupport.protocol.typeremapper.basic;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.stream.IntStream;

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
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupport.utils.CollectionsUtils.ArrayMap.Entry;

public class TileEntityRemapper {

	protected static final EnumMap<ProtocolVersion, TileEntityRemapper> tileEntityRemappers = new EnumMap<>(ProtocolVersion.class);

	static {
		for (ProtocolVersion version : ProtocolVersion.getAllSupported()) {
			tileEntityRemappers.put(version, new TileEntityRemapper());
		}
	}

	public static TileEntityRemapper getRemapper(ProtocolVersion version) {
		return tileEntityRemappers.get(version);
	}

	protected static void register(TileEntityType type, Consumer<TileEntity> transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			tileEntityRemappers.get(version).tileToTile
				.computeIfAbsent(type, k -> new ArrayList<>())
				.add((tile, blockdata) -> transformer.accept(tile));
		}
	}

	protected static void register(TileEntityType type, TileEntityWithBlockDataNBTRemapper transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			TileEntityRemapper remapper = tileEntityRemappers.get(version);
			IntStream.rangeClosed(transformer.remappers.getMinKey(), transformer.remappers.getMaxKey())
				.filter(i -> transformer.remappers.get(i) != null)
				.forEach(remapper.tileNeedsBlockData::add);
			remapper.tileToTile
				.computeIfAbsent(type, k -> new ArrayList<>())
				.add(transformer);
		}
	}

	protected abstract static class TileEntityWithBlockDataNBTRemapper implements ObjIntConsumer<TileEntity> {
		protected final ArrayMap<Consumer<NBTCompound>> remappers;

		protected TileEntityWithBlockDataNBTRemapper() {
			List<ArrayMap.Entry<Consumer<NBTCompound>>> list = new ArrayList<>();
			init(list);
			remappers = new ArrayMap<>(list);
		}

		protected abstract void init(List<ArrayMap.Entry<Consumer<NBTCompound>>> list);

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
			tileEntityRemappers.get(version).blockdataToTile.put(MaterialAPI.getBlockDataNetworkId(blockdata), transformer);
		}
	}


	protected static class BedTileEntitySupplier implements Function<Position, TileEntity> {

		protected final int color;

		public BedTileEntitySupplier(int color) {
			this.color = color;
		}

		@Override
		public TileEntity apply(Position position) {
			NBTCompound tag = new NBTCompound();
			tag.setTag("color", new NBTInt(color));
			return new TileEntity(TileEntityType.BED, position, tag);
		}

	}

	protected static class TileEntityToLegacyTypeNameRemapper implements Consumer<TileEntity> {
		protected final String name;

		public TileEntityToLegacyTypeNameRemapper(String name) {
			this.name = name;
		}

		@Override
		public void accept(TileEntity tile) {
			tile.getNBT().setTag("id", new NBTString(name));
		}
	}

	static {
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
		register(TileEntityType.MOB_SPAWNER, new TileEntityToLegacyTypeNameRemapper("MobSpawner"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.COMMAND_BLOCK, new TileEntityToLegacyTypeNameRemapper("Control"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.BEACON, new TileEntityToLegacyTypeNameRemapper("Beacon"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.SKULL, new TileEntityToLegacyTypeNameRemapper("Skull"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.BANNER, new TileEntityToLegacyTypeNameRemapper("Banner"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.STRUCTURE, new TileEntityToLegacyTypeNameRemapper("Structure"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.END_GATEWAY, new TileEntityToLegacyTypeNameRemapper("Airportal"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.SIGN, new TileEntityToLegacyTypeNameRemapper("Sign"), ProtocolVersionsHelper.BEFORE_1_11);
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
			TileEntityType.BANNER, new TileEntityWithBlockDataNBTRemapper() {
				protected void register(List<Entry<Consumer<NBTCompound>>> list, Material banner, int color) {
					for (BlockData blockdata : MaterialAPI.getBlockDataList(banner)) {
						list.add(new ArrayMap.Entry<>(MaterialAPI.getBlockDataNetworkId(blockdata), nbt -> nbt.setTag("Base", new NBTInt(color))));
					}
				}

				@Override
				protected void init(List<Entry<Consumer<NBTCompound>>> list) {
					register(list, Material.WHITE_BANNER, 15);
					register(list, Material.ORANGE_BANNER, 14);
					register(list, Material.MAGENTA_BANNER, 13);
					register(list, Material.LIGHT_BLUE_BANNER, 12);
					register(list, Material.YELLOW_BANNER, 11);
					register(list, Material.LIME_BANNER, 10);
					register(list, Material.PINK_BANNER, 9);
					register(list, Material.GRAY_BANNER, 8);
					register(list, Material.LIGHT_GRAY_BANNER, 7);
					register(list, Material.CYAN_BANNER, 6);
					register(list, Material.PURPLE_BANNER, 5);
					register(list, Material.BLUE_BANNER, 4);
					register(list, Material.BROWN_BANNER, 3);
					register(list, Material.GREEN_BANNER, 2);
					register(list, Material.RED_BANNER, 1);
					register(list, Material.BLACK_BANNER, 0);
					register(list, Material.WHITE_WALL_BANNER, 15);
					register(list, Material.ORANGE_WALL_BANNER, 14);
					register(list, Material.MAGENTA_WALL_BANNER, 13);
					register(list, Material.LIGHT_BLUE_WALL_BANNER, 12);
					register(list, Material.YELLOW_WALL_BANNER, 11);
					register(list, Material.LIME_WALL_BANNER, 10);
					register(list, Material.PINK_WALL_BANNER, 9);
					register(list, Material.GRAY_WALL_BANNER, 8);
					register(list, Material.LIGHT_GRAY_WALL_BANNER, 7);
					register(list, Material.CYAN_WALL_BANNER, 6);
					register(list, Material.PURPLE_WALL_BANNER, 5);
					register(list, Material.BLUE_WALL_BANNER, 4);
					register(list, Material.BROWN_WALL_BANNER, 3);
					register(list, Material.GREEN_WALL_BANNER, 2);
					register(list, Material.RED_WALL_BANNER, 1);
					register(list, Material.BLACK_WALL_BANNER, 0);
				}
			},
			ProtocolVersionsHelper.BEFORE_1_13
		);
		register(
			TileEntityType.SKULL, new TileEntityWithBlockDataNBTRemapper() {
				protected void register(List<Entry<Consumer<NBTCompound>>> list, Material skull, int skulltype) {
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
						list.add(new Entry<>(MaterialAPI.getBlockDataNetworkId(blockdata), nbt -> {
							nbt.setTag("SkullType", new NBTByte((byte) skulltype));
							nbt.setTag("Rot", new NBTByte(rotationF));
						}));
					}
				}

				@Override
				protected void init(List<Entry<Consumer<NBTCompound>>> list) {
					register(list, Material.SKELETON_SKULL, 0);
					register(list, Material.WITHER_SKELETON_SKULL, 1);
					register(list, Material.ZOMBIE_HEAD, 2);
					register(list, Material.PLAYER_HEAD, 3);
					register(list, Material.CREEPER_HEAD, 4);
					register(list, Material.DRAGON_HEAD, 5);
					register(list, Material.SKELETON_WALL_SKULL, 0);
					register(list, Material.WITHER_SKELETON_WALL_SKULL, 1);
					register(list, Material.ZOMBIE_WALL_HEAD, 2);
					register(list, Material.PLAYER_WALL_HEAD, 3);
					register(list, Material.CREEPER_WALL_HEAD, 4);
					register(list, Material.DRAGON_WALL_HEAD, 5);
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

		registerLegacyState(Material.WHITE_BED, new BedTileEntitySupplier(0), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.ORANGE_BED, new BedTileEntitySupplier(1), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.MAGENTA_BED, new BedTileEntitySupplier(2), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.LIGHT_BLUE_BED, new BedTileEntitySupplier(3), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.YELLOW_BED, new BedTileEntitySupplier(4), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.LIME_BED, new BedTileEntitySupplier(5), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.PINK_BED, new BedTileEntitySupplier(6), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.GRAY_BED, new BedTileEntitySupplier(7), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.LIGHT_GRAY_BED, new BedTileEntitySupplier(8), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.CYAN_BED, new BedTileEntitySupplier(9), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.PURPLE_BED, new BedTileEntitySupplier(10), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.BLUE_BED, new BedTileEntitySupplier(11), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.BROWN_BED, new BedTileEntitySupplier(12), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.GREEN_BED, new BedTileEntitySupplier(13), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.RED_BED, new BedTileEntitySupplier(14), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.BLACK_BED, new BedTileEntitySupplier(15), ProtocolVersionsHelper.ALL_1_12);
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
