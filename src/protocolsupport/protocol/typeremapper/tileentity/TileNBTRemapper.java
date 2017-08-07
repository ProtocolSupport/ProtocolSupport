package protocolsupport.protocol.typeremapper.tileentity;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.typeremapper.itemstack.toclient.DragonHeadSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.toclient.PlayerSkullToLegacyOwnerSpecificRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityType;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.ItemData;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.IntTuple;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagType;

public class TileNBTRemapper {

	private static final String tileEntityTypeKey = "id";

	private static final HashMap<String, String> newToOldType = new HashMap<>();
	static {
		newToOldType.put("minecraft:mob_spawner", "MobSpawner");
		newToOldType.put("minecraft:command_block", "Control");
		newToOldType.put("minecraft:beacon", "Beacon");
		newToOldType.put("minecraft:skull", "Skull");
		newToOldType.put("minecraft:flower_pot", "FlowerPot");
		newToOldType.put("minecraft:banner", "Banner");
		newToOldType.put("minecraft:structure_block", "Structure");
		newToOldType.put("minecraft:end_gateway", "Airportal");
		newToOldType.put("minecraft:sign", "Sign");
	}

	private static final HashMap<String, String> peTypes = new HashMap<>();
	static {
		peTypes.put("minecraft:chest", "Chest");
		peTypes.put("minecraft:ender_chest", "EnderChest");
		peTypes.put("minecraft:furnace", "Furnace");
		peTypes.put("minecraft:sign", "Sign");
		peTypes.put("minecraft:mob_spawner", "MobSpawner");
		peTypes.put("minecraft:enchanting_table", "EnchantTable");
		peTypes.put("minecraft:skull", "Skull");
		peTypes.put("minecraft:flower_pot", "FlowerPot");
		peTypes.put("minecraft:brewing_stand", "BrewingStand");
		peTypes.put("minecraft:daylight_detector", "DaylightDetector");
		peTypes.put("minecraft:noteblock", "Music");
		peTypes.put("minecraft:beacon", "Beacon");
	}

	private static final EnumMap<TileEntityUpdateType, EnumMap<ProtocolVersion, List<BiFunction<ProtocolVersion, NBTTagCompoundWrapper, NBTTagCompoundWrapper>>>> registry = new EnumMap<>(TileEntityUpdateType.class);

	private static void register(TileEntityUpdateType type, BiFunction<ProtocolVersion, NBTTagCompoundWrapper, NBTTagCompoundWrapper> transformer, ProtocolVersion... versions) {
		EnumMap<ProtocolVersion, List<BiFunction<ProtocolVersion, NBTTagCompoundWrapper, NBTTagCompoundWrapper>>> map = Utils.getFromMapOrCreateDefault(registry, type, new EnumMap<>(ProtocolVersion.class));
		for (ProtocolVersion version : versions) {
			Utils.getFromMapOrCreateDefault(map, version, new ArrayList<>()).add(transformer);
		}
	}

	static {
		for (TileEntityUpdateType type : TileEntityUpdateType.values()) {
			register(
				type,
				(version, input) -> {
					input.setString(tileEntityTypeKey, newToOldType.getOrDefault(input.getString(tileEntityTypeKey), "Unknown"));
					return input;
				},
				ProtocolVersionsHelper.BEFORE_1_11
			);
			register(
				type,
				(version, input) -> {
					input.setString(tileEntityTypeKey, peTypes.getOrDefault(input.getString(tileEntityTypeKey), "Unknown"));
					return input;
				},
				ProtocolVersion.MINECRAFT_PE
			);
		}
		register(
			TileEntityUpdateType.MOB_SPAWNER,
			(version, input) -> {
				if (!input.hasKeyOfType("SpawnData", NBTTagType.COMPOUND)) {
					NBTTagCompoundWrapper spawndata = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
					spawndata.setString("id", "minecraft:pig");
					input.setCompound("SpawnData", spawndata);
				}
				return input;
			},
			ProtocolVersionsHelper.ALL_PC
		);
		register(
			TileEntityUpdateType.MOB_SPAWNER,
			(version, input) -> {
				NBTTagCompoundWrapper spawndata = input.getCompound("SpawnData");
				if (!spawndata.isNull()) {
					String mobname = spawndata.getString("id");
					if (!mobname.isEmpty()) {
						spawndata.setString("id", LegacyEntityType.getLegacyName(mobname));
					}
				}
				return input;
			},
			ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9, ProtocolVersion.MINECRAFT_1_10)
		);
		register(
			TileEntityUpdateType.MOB_SPAWNER,
			(version, input) -> {
				NBTTagCompoundWrapper spawndata = input.getCompound("SpawnData");
				input.remove("SpawnPotentials");
				input.remove("SpawnData");
				if (!spawndata.isNull()) {
					String mobname = spawndata.getString("id");
					if (!mobname.isEmpty()) {
						input.setString("EntityId", LegacyEntityType.getLegacyName(mobname));
					}
				}
				return input;
			},
			ProtocolVersionsHelper.BEFORE_1_9
		);
		register(
			TileEntityUpdateType.SKULL,
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
			TileEntityUpdateType.SKULL,
			(version, input) -> {
				PlayerSkullToLegacyOwnerSpecificRemapper.remap(input, "Owner", "ExtraType");
				return input;
			},
			ProtocolVersion.getAllBeforeI(ProtocolVersion.MINECRAFT_1_7_5)
		);
		register(
			TileEntityUpdateType.FLOWER_POT,
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
				TileEntityUpdateType.SIGN,
				(version, input) -> {
					String[] lines = getSignLines(input);
					StringBuilder stringBuilder = new StringBuilder();
					for (int i = 1; 4 >= i; i++) {
						String line = ChatAPI.fromJSON(lines[i  - 1]).toLegacyText();
						if (stringBuilder.length() == 0) {
							stringBuilder.append(line);
						} else {
							stringBuilder.append("\n");
							stringBuilder.append(line);
						}
						input.remove("Text" + i); // Cleanup old NBT value
					}
					input.setString("Text", stringBuilder.toString());
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
		EnumMap<ProtocolVersion, List<BiFunction<ProtocolVersion, NBTTagCompoundWrapper, NBTTagCompoundWrapper>>> map = registry.get(TileEntityUpdateType.fromType(getTileType(compound)));
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
