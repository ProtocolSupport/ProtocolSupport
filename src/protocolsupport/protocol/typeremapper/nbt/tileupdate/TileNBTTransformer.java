package protocolsupport.protocol.typeremapper.nbt.tileupdate;

import java.util.EnumMap;

import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.server.v1_10_R1.Item;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;
import protocolsupport.utils.ProtocolVersionsHelper;

public class TileNBTTransformer {

	public static enum TileEntityUpdateType {
		UNKNOWN, MOB_SPAWNER, COMMAND_BLOCK, BEACON, SKULL, FLOWER_POT, BANNER, STRUCTURE, END_GATEWAY, SIGN; 
	}

	private static final TIntObjectHashMap<EnumMap<ProtocolVersion, SpecificTransformer>> registry = new TIntObjectHashMap<>();

	private static void register(TileEntityUpdateType type, SpecificTransformer transformer, ProtocolVersion... versions) {
		registry.putIfAbsent(type.ordinal(), new EnumMap<ProtocolVersion, SpecificTransformer>(ProtocolVersion.class));
		EnumMap<ProtocolVersion, SpecificTransformer> map = registry.get(type.ordinal());
		for (ProtocolVersion version : versions) {
			map.put(version, transformer);
		}
	}

	static {
		register(
			TileEntityUpdateType.MOB_SPAWNER,
			new SpecificTransformer() {
				@Override
				public NBTTagCompoundWrapper transform(ProtocolVersion version, NBTTagCompoundWrapper input) {
					NBTTagCompoundWrapper spawndata = input.getCompound("SpawnData");
					input.remove("SpawnPotentials");
					input.remove("SpawnData");
					if (!spawndata.isNull()) {
						String mobname = spawndata.getString("id");
						if (!mobname.isEmpty()) {
							input.setString("EntityId", mobname);
						}
					}
					return input;
				}
			},
			ProtocolVersionsHelper.BEFORE_1_9
		);
		register(
			TileEntityUpdateType.SKULL,
			new SpecificTransformer() {
				@Override
				public NBTTagCompoundWrapper transform(ProtocolVersion version, NBTTagCompoundWrapper input) {
					ProtocolSupportPacketDataSerializer.transformSkull(input.unwrap());
					return input;
				}
			},
			ProtocolVersion.getAllBefore(ProtocolVersion.MINECRAFT_1_7_5)
		);
		register(
			TileEntityUpdateType.FLOWER_POT,
			new SpecificTransformer() {
				@Override
				public NBTTagCompoundWrapper transform(ProtocolVersion version, NBTTagCompoundWrapper input) {
					String itemId = input.getString("Item");
					if (!itemId.isEmpty()) {
						input.setInt("Item", IdRemapper.ITEM.getTable(version).getRemap(Item.getId(Item.d(itemId))));
					}
					return input;
				}
			},
			ProtocolVersionsHelper.BEFORE_1_8
		);
	}

	public static NBTTagCompoundWrapper transform(int type, ProtocolVersion version, NBTTagCompoundWrapper compound) {
		EnumMap<ProtocolVersion, SpecificTransformer> map = registry.get(type);
		if (map != null) {
			SpecificTransformer transformer = map.get(version);
			if (transformer != null) {
				return transformer.transform(version, compound);
			}
		}
		return compound;
	}

}
