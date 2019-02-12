package protocolsupport.protocol.typeremapper.tile;

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

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import protocolsupport.api.MaterialAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.DragonHeadToDragonPlayerHeadComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PlayerHeadToLegacyOwnerComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntity;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.protocol.utils.types.nbt.NBTByte;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.NBTNumber;
import protocolsupport.protocol.utils.types.nbt.NBTString;
import protocolsupport.protocol.utils.types.nbt.NBTType;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupportbuildprocessor.Preload;

@Preload
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

	protected static void registerPerVersion(TileEntityType type, Function<ProtocolVersion, Consumer<TileEntity>> transformer, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			register(type, transformer.apply(version), version);
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


	static {
		registerPerVersion(
			TileEntityType.MOB_SPAWNER,
			version -> {
				EntityRemappingTable entityRemapTable = EntityRemappersRegistry.REGISTRY.getTable(version);
				return tile -> {
					NBTCompound nbt = tile.getNBT();
					NBTCompound spawndataTag = nbt.getTagOfType(CommonNBT.MOB_SPAWNER_SPAWNDATA, NBTType.COMPOUND);
					NetworkEntityType type = null;
					if (spawndataTag == null) {
						spawndataTag = new NBTCompound();
						nbt.setTag(CommonNBT.MOB_SPAWNER_SPAWNDATA, spawndataTag);
						type = NetworkEntityType.PIG;
					} else {
						type = CommonNBT.getSpawnedMobType(spawndataTag);
					}
					spawndataTag.setTag("id", new NBTString(entityRemapTable.getRemap(type).getLeft().getKey()));
				};
			},
			ProtocolVersionsHelper.ALL_PC
		);

		register(TileEntityType.PISTON, new TileEntityPistonRemapper(), ProtocolVersionsHelper.BEFORE_1_13);

		register(TileEntityType.BANNER, new TileEntityBannerRemapper(), ProtocolVersionsHelper.BEFORE_1_13);
		register(TileEntityType.SKULL, new TileEntitySkullRemapper(), ProtocolVersionsHelper.BEFORE_1_13);

		register(TileEntityType.MOB_SPAWNER, new TileEntityToLegacyTypeNameRemapper("MobSpawner"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.COMMAND_BLOCK, new TileEntityToLegacyTypeNameRemapper("Control"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.BEACON, new TileEntityToLegacyTypeNameRemapper("Beacon"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.SKULL, new TileEntityToLegacyTypeNameRemapper("Skull"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.BANNER, new TileEntityToLegacyTypeNameRemapper("Banner"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.STRUCTURE, new TileEntityToLegacyTypeNameRemapper("Structure"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.END_GATEWAY, new TileEntityToLegacyTypeNameRemapper("Airportal"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.SIGN, new TileEntityToLegacyTypeNameRemapper("Sign"), ProtocolVersionsHelper.BEFORE_1_11);
		register(TileEntityType.PISTON, new TileEntityToLegacyTypeNameRemapper("Piston"), ProtocolVersionsHelper.BEFORE_1_11);

		register(
			TileEntityType.MOB_SPAWNER,
			tile -> {
				NBTCompound spawndata = tile.getNBT().getTagOfType(CommonNBT.MOB_SPAWNER_SPAWNDATA, NBTType.COMPOUND);
				if (spawndata != null) {
					NetworkEntityType type = CommonNBT.getSpawnedMobType(spawndata);
					if (type != NetworkEntityType.NONE) {
						spawndata.setTag("id", new NBTString(LegacyEntityId.getStringId(type)));
					}
				}
			},
			ProtocolVersionsHelper.RANGE__1_9__1_10
		);
		register(
			TileEntityType.MOB_SPAWNER,
			tile -> {
				NBTCompound nbt = tile.getNBT();
				NBTCompound spawndata = tile.getNBT().getTagOfType(CommonNBT.MOB_SPAWNER_SPAWNDATA, NBTType.COMPOUND);
				if (spawndata != null) {
					nbt.removeTag(CommonNBT.MOB_SPAWNER_SPAWNDATA);
					nbt.removeTag("SpawnPotentials");
					NetworkEntityType type = CommonNBT.getSpawnedMobType(spawndata);
					if (type != NetworkEntityType.NONE) {
						nbt.setTag("EntityId", new NBTString(LegacyEntityId.getStringId(type)));
					}
				}
			},
			ProtocolVersionsHelper.BEFORE_1_9
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

		registerLegacyState(Material.WHITE_BED, new TileEntityBedSupplier(0), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.ORANGE_BED, new TileEntityBedSupplier(1), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.MAGENTA_BED, new TileEntityBedSupplier(2), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.LIGHT_BLUE_BED, new TileEntityBedSupplier(3), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.YELLOW_BED, new TileEntityBedSupplier(4), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.LIME_BED, new TileEntityBedSupplier(5), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.PINK_BED, new TileEntityBedSupplier(6), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.GRAY_BED, new TileEntityBedSupplier(7), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.LIGHT_GRAY_BED, new TileEntityBedSupplier(8), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.CYAN_BED, new TileEntityBedSupplier(9), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.PURPLE_BED, new TileEntityBedSupplier(10), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.BLUE_BED, new TileEntityBedSupplier(11), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.BROWN_BED, new TileEntityBedSupplier(12), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.GREEN_BED, new TileEntityBedSupplier(13), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.RED_BED, new TileEntityBedSupplier(14), ProtocolVersionsHelper.ALL_1_12);
		registerLegacyState(Material.BLACK_BED, new TileEntityBedSupplier(15), ProtocolVersionsHelper.ALL_1_12);
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
