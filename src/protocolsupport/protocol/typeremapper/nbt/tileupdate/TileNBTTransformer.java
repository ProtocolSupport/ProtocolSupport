package protocolsupport.protocol.typeremapper.nbt.tileupdate;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacyEntityType;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.ProtocolVersionsHelper;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class TileNBTTransformer {

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

	private static final EnumMap<TileEntityUpdateType, EnumMap<ProtocolVersion, List<SpecificTransformer>>> registry = new EnumMap<>(TileEntityUpdateType.class);

	private static void register(TileEntityUpdateType type, SpecificTransformer transformer, ProtocolVersion... versions) {
		EnumMap<ProtocolVersion, List<SpecificTransformer>> map = Utils.getOrCreateDefault(registry, type, new EnumMap<ProtocolVersion, List<SpecificTransformer>>(ProtocolVersion.class));
		for (ProtocolVersion version : versions) {
			Utils.getOrCreateDefault(map, version, new ArrayList<SpecificTransformer>()).add(transformer);
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
		}
		register(TileEntityUpdateType.MOB_SPAWNER,
			(version, input) -> {
				if (!input.hasKeyOfType("SpawnData", NBTTagCompoundWrapper.TYPE_COMPOUND)) {
					NBTTagCompoundWrapper spawndata = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
					spawndata.setString("id", "minecraft:pig");
					input.setCompound("SpawnData", spawndata);
				}
				return input;
			},
			ProtocolVersionsHelper.ALL
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
				if (input.getNumber("SkullType") == 5) {
					input.setByte("SkullType", 3);
					input.setCompound("Owner", ProtocolSupportPacketDataSerializer.createDragonHeadSkullTag());
				}
				return input;
			},
			ProtocolVersion.getAllBefore(ProtocolVersion.MINECRAFT_1_9)
		);
		register(
			TileEntityUpdateType.SKULL,
			(version, input) -> {
				ProtocolSupportPacketDataSerializer.transformSkull(input);
				return input;
			},
			ProtocolVersion.getAllBefore(ProtocolVersion.MINECRAFT_1_7_5)
		);
		register(
			TileEntityUpdateType.FLOWER_POT,
			(version, input) -> {
				Integer id = ServerPlatform.get().getMiscUtils().getItemIdByName(input.getString("Item"));
				if (id != null) {
					input.setInt("Item", IdRemapper.ITEM.getTable(version).getRemap(id));
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
		return new Position(tag.getNumber("x"), tag.getNumber("y"), tag.getNumber("z"));
	}

	public static String[] getSignLines(NBTTagCompoundWrapper tag) {
		String[] lines = new String[4];
		for (int i = 0; i < lines.length; i++) {
			lines[i] = tag.getString("Text" + (i + 1));
		}
		return lines;
	}

	public static NBTTagCompoundWrapper transform(ProtocolVersion version, NBTTagCompoundWrapper compound) {
		EnumMap<ProtocolVersion, List<SpecificTransformer>> map = registry.get(TileEntityUpdateType.fromType(getTileType(compound)));
		if (map != null) {
			List<SpecificTransformer> transformers = map.get(version);
			if (transformers != null) {
				for (SpecificTransformer transformer : transformers) {
					compound = transformer.transform(version, compound);
				}
				return compound;
			}
		}
		return compound;
	}

}
