package protocolsupport.protocol.typeremapper.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.DragonHeadToDragonPlayerHeadComplexRemapper;
import protocolsupport.protocol.typeremapper.itemstack.complex.toclient.PlayerHeadToLegacyOwnerComplexRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.TileEntityType;
import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTNumber;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
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
			remapper.tileNeedsBlockData.add(type);
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
						if (type == NetworkEntityType.NONE) {
							type = NetworkEntityType.PIG;
						}
					}
					spawndataTag.setTag("id", new NBTString(entityRemapTable.getRemap(type).getLeft().getKey()));
				};
			},
			ProtocolVersionsHelper.ALL_PC
		);

		register(TileEntityType.PISTON, new TileEntityPistonRemapper(), ProtocolVersionsHelper.DOWN_1_12_2);

		register(TileEntityType.BANNER, new TileEntityBannerRemapper(), ProtocolVersionsHelper.DOWN_1_12_2);
		register(TileEntityType.SKULL, new TileEntitySkullRemapper(), ProtocolVersionsHelper.DOWN_1_12_2);

		register(TileEntityType.BED, new TileEntityBedRemapper(), ProtocolVersionsHelper.ALL_1_12);

		register(TileEntityType.MOB_SPAWNER, new TileEntityToLegacyTypeNameRemapper("MobSpawner"), ProtocolVersionsHelper.DOWN_1_10);
		register(TileEntityType.COMMAND_BLOCK, new TileEntityToLegacyTypeNameRemapper("Control"), ProtocolVersionsHelper.DOWN_1_10);
		register(TileEntityType.BEACON, new TileEntityToLegacyTypeNameRemapper("Beacon"), ProtocolVersionsHelper.DOWN_1_10);
		register(TileEntityType.SKULL, new TileEntityToLegacyTypeNameRemapper("Skull"), ProtocolVersionsHelper.DOWN_1_10);
		register(TileEntityType.BANNER, new TileEntityToLegacyTypeNameRemapper("Banner"), ProtocolVersionsHelper.DOWN_1_10);
		register(TileEntityType.STRUCTURE, new TileEntityToLegacyTypeNameRemapper("Structure"), ProtocolVersionsHelper.DOWN_1_10);
		register(TileEntityType.END_GATEWAY, new TileEntityToLegacyTypeNameRemapper("Airportal"), ProtocolVersionsHelper.DOWN_1_10);
		register(TileEntityType.SIGN, new TileEntityToLegacyTypeNameRemapper("Sign"), ProtocolVersionsHelper.DOWN_1_10);
		register(TileEntityType.PISTON, new TileEntityToLegacyTypeNameRemapper("Piston"), ProtocolVersionsHelper.DOWN_1_10);

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
			ProtocolVersionsHelper.DOWN_1_8
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
	}

	protected final Map<TileEntityType, List<ObjIntConsumer<TileEntity>>> tileToTile = new EnumMap<>(TileEntityType.class);
	protected final Set<TileEntityType> tileNeedsBlockData = Collections.newSetFromMap(new EnumMap<>(TileEntityType.class));

	public TileEntity remap(TileEntity tileentity) {
		return remap(tileentity, -1);
	}

	public TileEntity remap(TileEntity tileentity, int blockdata) {
		List<ObjIntConsumer<TileEntity>> transformers = tileToTile.get(tileentity.getType());
		if (transformers != null) {
			for (ObjIntConsumer<TileEntity> transformer : transformers) {
				transformer.accept(tileentity, blockdata);
			}
		}
		return tileentity;
	}

	public boolean tileThatNeedsBlockData(TileEntityType type) {
		return tileNeedsBlockData.contains(type);
	}

}
