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
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry;
import protocolsupport.protocol.typeremapper.entity.format.NetworkEntityLegacyFormatRegistry.NetworkEntityLegacyFormatTable;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry;
import protocolsupport.protocol.typeremapper.entity.legacy.NetworkEntityLegacyDataRegistry.NetworkEntityLegacyDataTable;
import protocolsupport.protocol.typeremapper.itemstack.format.to.ItemStackLegacyFormatOperatorPlayerHeadToLegacyOwner;
import protocolsupport.protocol.typeremapper.itemstack.format.to.ItemStackLegacyFormatOperatorPlayerHeadToLegacyUUID;
import protocolsupport.protocol.typeremapper.itemstack.legacy.ItemStackLegacyDataOperatorDragonHeadToDragonPlayerHead;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.TileEntity;
import protocolsupport.protocol.types.TileEntityType;
import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTIntArray;
import protocolsupport.protocol.types.nbt.NBTList;
import protocolsupport.protocol.types.nbt.NBTNumber;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.GameProfileSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;
import protocolsupportbuildprocessor.Preload;

@Preload
public class TileEntityRemapper {

	protected static final EnumMap<ProtocolVersion, TileEntityRemapper> tileEntityRemappers = new EnumMap<>(ProtocolVersion.class);
	static {
		for (ProtocolVersion version : ProtocolVersionsHelper.ALL) {
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
				NetworkEntityLegacyDataTable legacyEntityEntryTable = NetworkEntityLegacyDataRegistry.INSTANCE.getTable(version);
				NetworkEntityLegacyFormatTable entityDataFormatTable = NetworkEntityLegacyFormatRegistry.INSTANCE.getTable(version);
				return tile -> {
					NBTCompound nbt = tile.getNBT();
					NBTCompound spawndataTag = nbt.getCompoundTagOrNull(CommonNBT.MOB_SPAWNER_SPAWNDATA);
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
					//TODO: also handle additioanl nbt data
					spawndataTag.setTag("id", new NBTString(entityDataFormatTable.get(legacyEntityEntryTable.get(type).getType()).getType().getKey()));
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
				NBTCompound spawndata = tile.getNBT().getCompoundTagOrNull(CommonNBT.MOB_SPAWNER_SPAWNDATA);
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
				NBTCompound spawndata = nbt.removeTagAndReturnIfType(CommonNBT.MOB_SPAWNER_SPAWNDATA, NBTCompound.class);
				if (spawndata != null) {
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
				NBTCompound tag = tile.getNBT();
				NBTCompound gameprofileTag = tag.getCompoundTagOrNull(CommonNBT.PLAYERHEAD_PROFILE);
				if (gameprofileTag == null) {
					return;
				}
				NBTCompound propertiesTag = gameprofileTag.getCompoundTagOrNull(GameProfileSerializer.PROPERTIES_KEY);
				if (propertiesTag == null) {
					return;
				}
				NBTList<NBTCompound> texturesTag = propertiesTag.getCompoundListTagOrNull("textures");
				if ((texturesTag == null) || texturesTag.isEmpty()) {
					return;
				}
				String textureValueTag = texturesTag.getTag(0).getStringTagValueOrNull(GameProfileSerializer.PROPERTY_VALUE_KEY);
				if (textureValueTag == null) {
					return;
				}
				gameprofileTag.setTag(GameProfileSerializer.UUID_KEY, new NBTIntArray(new int[] {0, 0, 0, textureValueTag.hashCode()}));
			},
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_16_1)
		);
		register(
			TileEntityType.SKULL,
			tile -> {
				NBTCompound nbt = tile.getNBT();
				NBTNumber skulltype = nbt.getNumberTagOrNull("SkullType");
				if ((skulltype != null) && (skulltype.getAsInt() == 5)) {
					nbt.setTag("SkullType", new NBTByte((byte) 3));
					nbt.setTag(CommonNBT.PLAYERHEAD_PROFILE, ItemStackLegacyDataOperatorDragonHeadToDragonPlayerHead.createTag());
				}
			},
			ProtocolVersionsHelper.DOWN_1_8
		);
		register(
			TileEntityType.SKULL,
			tile -> ItemStackLegacyFormatOperatorPlayerHeadToLegacyUUID.apply(tile.getNBT()),
			ProtocolVersionsHelper.RANGE__1_7_10__1_15_2
		);
		register(
			TileEntityType.SKULL,
			tile -> {
				NBTCompound tag = tile.getNBT();
				tag.setTag("Owner", tag.removeTagAndReturnIfType(CommonNBT.PLAYERHEAD_PROFILE, NBTCompound.class));
			},
			ProtocolVersionsHelper.RANGE__1_7_10__1_15_2
		);
		register(
			TileEntityType.SKULL,
			tile -> ItemStackLegacyFormatOperatorPlayerHeadToLegacyOwner.apply(tile.getNBT(), "ExtraType"),
			ProtocolVersionsHelper.DOWN_1_7_5
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
