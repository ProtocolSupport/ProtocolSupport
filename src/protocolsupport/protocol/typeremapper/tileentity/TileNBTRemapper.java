package protocolsupport.protocol.typeremapper.tileentity;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BiFunction;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.typeremapper.itemstack.toclient.DragonHeadSpecificRemapper;
import protocolsupport.protocol.typeremapper.itemstack.toclient.PlayerSkullToLegacyOwnerSpecificRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityType;
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
			ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_9, ProtocolVersion.MINECRAFT_1_10)
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
