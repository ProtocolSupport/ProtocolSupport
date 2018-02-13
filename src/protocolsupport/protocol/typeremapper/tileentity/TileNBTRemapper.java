package protocolsupport.protocol.typeremapper.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.typeremapper.itemstack.toclient.DragonHeadSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.toclient.PlayerSkullToLegacyOwnerSpecificRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityType;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.ItemData;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.protocol.utils.types.TileEntityType;
import protocolsupport.utils.IntTuple;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class TileNBTRemapper {

	private static final String tileEntityTypeKey = "id";

	private static final EnumMap<TileEntityType, String> newToOldType = new EnumMap<>(TileEntityType.class);
	static {
		newToOldType.put(TileEntityType.MOB_SPAWNER, "MobSpawner");
		newToOldType.put(TileEntityType.COMMAND_BLOCK, "Control");
		newToOldType.put(TileEntityType.BEACON, "Beacon");
		newToOldType.put(TileEntityType.SKULL, "Skull");
		newToOldType.put(TileEntityType.FLOWER_POT, "FlowerPot");
		newToOldType.put(TileEntityType.BANNER, "Banner");
		newToOldType.put(TileEntityType.STRUCTURE, "Structure");
		newToOldType.put(TileEntityType.END_GATEWAY, "Airportal");
		newToOldType.put(TileEntityType.SIGN, "Sign");
	}

	private static final EnumMap<TileEntityType, String> peTypes = new EnumMap<>(TileEntityType.class);
	static {
		peTypes.put(TileEntityType.CHEST, "Chest");
		peTypes.put(TileEntityType.ENDER_CHEST, "EnderChest");
		peTypes.put(TileEntityType.FURNACE, "Furnace");
		peTypes.put(TileEntityType.SIGN, "Sign");
		peTypes.put(TileEntityType.MOB_SPAWNER, "MobSpawner");
		peTypes.put(TileEntityType.ENCHANTING_TABLE, "EnchantTable");
		peTypes.put(TileEntityType.SKULL, "Skull");
		peTypes.put(TileEntityType.FLOWER_POT, "FlowerPot");
		peTypes.put(TileEntityType.BREWING_STAND, "BrewingStand");
		peTypes.put(TileEntityType.DAYLIGHT_DETECTOR, "DaylightDetector");
		peTypes.put(TileEntityType.NOTE_BLOCK, "Music");
		peTypes.put(TileEntityType.BEACON, "Beacon");
		peTypes.put(TileEntityType.SHULKER_BOX, "ShulkerBox");
		peTypes.put(TileEntityType.BED, "Bed");
		peTypes.put(TileEntityType.BANNER, "Banner");
	}

	private static final EnumMap<TileEntityType, EnumMap<ProtocolVersion, List<BiFunction<ProtocolVersion, NBTTagCompoundWrapper, NBTTagCompoundWrapper>>>> registry = new EnumMap<>(TileEntityType.class);

	private static void register(TileEntityType type, BiFunction<ProtocolVersion, NBTTagCompoundWrapper, NBTTagCompoundWrapper> transformer, ProtocolVersion... versions) {
		EnumMap<ProtocolVersion, List<BiFunction<ProtocolVersion, NBTTagCompoundWrapper, NBTTagCompoundWrapper>>> map = Utils.getFromMapOrCreateDefault(registry, type, new EnumMap<>(ProtocolVersion.class));
		for (ProtocolVersion version : versions) {
			Utils.getFromMapOrCreateDefault(map, version, new ArrayList<>()).add(transformer);
		}
	}

	static {
		for (TileEntityType type : TileEntityType.values()) {
			register(
				type,
				(version, input) -> {
					input.setString(tileEntityTypeKey, newToOldType.getOrDefault(type, type.getRegistryId()));
					return input;
				},
				ProtocolVersionsHelper.BEFORE_1_11
			);
			register(
				type,
				(version, input) -> {
					input.setString(tileEntityTypeKey, peTypes.getOrDefault(type, type.getRegistryId()));
					return input;
				},
				ProtocolVersion.MINECRAFT_PE
			);
		}
		register(
			TileEntityType.MOB_SPAWNER,
			(version, input) -> {
				if (!input.hasKeyOfType("SpawnData", NBTTagType.COMPOUND)) {
					NBTTagCompoundWrapper spawndata = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
					spawndata.setString("id", NetworkEntityType.PIG.getRegistrySTypeId());
					input.setCompound("SpawnData", spawndata);
				}
				return input;
			},
			ProtocolVersionsHelper.ALL_PC
		);
		register(
			TileEntityType.MOB_SPAWNER,
			(version, input) -> {
				NBTTagCompoundWrapper spawndata = input.getCompound("SpawnData");
				NetworkEntityType type = NetworkEntityType.getByRegistrySTypeId(spawndata.getString("id"));
				if (type != NetworkEntityType.NONE) {
					spawndata.setString("id", LegacyEntityType.getLegacyName(type));
				}
				return input;
			},
			ProtocolVersionsHelper.concat(ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9, ProtocolVersion.MINECRAFT_1_10))
		);
		register(
			TileEntityType.MOB_SPAWNER,
			(version, input) -> {
				NBTTagCompoundWrapper spawndata = input.getCompound("SpawnData");
				input.remove("SpawnPotentials");
				input.remove("SpawnData");
				NetworkEntityType type = NetworkEntityType.getByRegistrySTypeId(spawndata.getString("id"));
				if (type != NetworkEntityType.NONE) {
					input.setString("EntityId", LegacyEntityType.getLegacyName(type));
				}
				return input;
			},
			ProtocolVersionsHelper.BEFORE_1_9
		);
		register(
			TileEntityType.SKULL,
			(version, input) -> {
				if (input.getIntNumber("SkullType") == 5) {
					input.setByte("SkullType", 3);
					input.setCompound("Owner", DragonHeadSpecificRemapper.createTag());
				}
				return input;
			},
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_8)
		);
		register(
			TileEntityType.SKULL,
			(version, input) -> {
				PlayerSkullToLegacyOwnerSpecificRemapper.remap(input, "Owner", "ExtraType");
				return input;
			},
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_7_5)
		);
		register(
			TileEntityType.FLOWER_POT,
			(version, input) -> {
				Integer id = ItemData.getIdByName(input.getString("Item"));
				if (id != null) {
					IntTuple iddata = ItemStackRemapper.ID_DATA_REMAPPING_REGISTRY.getTable(version).getRemap(id, 0);
					input.setInt("Item", iddata != null ? iddata.getI1() : id);
				}
				return input;
			},
			ProtocolVersionsHelper.BEFORE_1_8
		);
		register(
			TileEntityType.SIGN,
			(version, input) -> {
				input.setString("Text", String.join("\n",
					Arrays.stream(getSignLines(input))
					.map(line -> ChatAPI.fromJSON(line).toLegacyText())
					.collect(Collectors.toList())
				));
				return input;
			}, ProtocolVersion.MINECRAFT_PE
		);
		register(
				TileEntityType.MOB_SPAWNER,
				(version, input) -> {
					input.remove("SpawnPotentials");

					int entityId = 0;

					if (input.hasKeyOfType("SpawnData", NBTTagType.COMPOUND)) {
						NBTTagCompoundWrapper compound = input.getCompound("SpawnData");
						entityId = PEDataValues.getLivingEntityTypeId(NetworkEntityType.getByRegistrySTypeId(compound.getString("id")));
						compound.setInt("Type", entityId);
					}

					input.setInt("EntityId", entityId);
					input.setFloat("DisplayEntityWidth", 1);
					input.setFloat("DisplayEntityHeight", 1);
					input.setFloat("DisplayEntityScale", 1);
					return input;
				},
				ProtocolVersion.MINECRAFT_PE
		);
		register(
				TileEntityType.BED,
				(version, input) -> {
					int color = input.getIntNumber("color");
					input.remove("color");
					input.setByte("color", color);
					return input;
				},
				ProtocolVersion.MINECRAFT_PE
		);
		register(
				TileEntityType.FLOWER_POT,
				(version, input) -> {
					String itemName = input.getString("Item");
					int data = input.getIntNumber("Data");
					input.remove("Item");
					input.remove("Data");
					Integer id = ItemData.getIdByName(itemName);
					if (id != null) {
						input.setShort("item", id);
						input.setInt("mData", data);
					}
					return input;
				},
				ProtocolVersion.MINECRAFT_PE
		);
	}

	public static String getTileType(NBTTagCompoundWrapper tag) {
		return tag.getString(tileEntityTypeKey);
	}

	public static Position getPosition(NBTTagCompoundWrapper tag) {
		return new Position(tag.getIntNumber("x"), tag.getIntNumber("y"), tag.getIntNumber("z"));
	}

	public static String[] getSignLines(NBTTagCompoundWrapper tag) {
		String[] lines = new String[4];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = tag.getString("Text" + (i + 1));
		}
		return lines;
	}

	public static NBTTagCompoundWrapper remap(ProtocolVersion version, NBTTagCompoundWrapper compound) {
		EnumMap<ProtocolVersion, List<BiFunction<ProtocolVersion, NBTTagCompoundWrapper, NBTTagCompoundWrapper>>> map = registry.get(TileEntityType.getByRegistryId(getTileType(compound)));
		if (map != null) {
			List<BiFunction<ProtocolVersion, NBTTagCompoundWrapper, NBTTagCompoundWrapper>> transformers = map.get(version);
			if (transformers != null) {
				for (BiFunction<ProtocolVersion, NBTTagCompoundWrapper, NBTTagCompoundWrapper> transformer : transformers) {
					compound = transformer.apply(version, compound);
				}
				return compound;
			}
		}
		return compound;
	}

}
