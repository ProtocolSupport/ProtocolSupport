package protocolsupport.protocol.typeremapper.nbt.tileupdate;

import java.util.EnumMap;

import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.utils.ProtocolVersionsHelper;

public class TileNBTTransformer {

	private static final TIntObjectHashMap<EnumMap<ProtocolVersion, SpecificTransformer>> registry = new TIntObjectHashMap<>();

	private static void register(int type, SpecificTransformer transformer, ProtocolVersion... versions) {
		registry.putIfAbsent(type, new EnumMap<ProtocolVersion, SpecificTransformer>(ProtocolVersion.class));
		EnumMap<ProtocolVersion, SpecificTransformer> map = registry.get(type);
		for (ProtocolVersion version : versions) {
			map.put(version, transformer);
		}
	}

	static {
		register(
			1,
			new SpecificTransformer() {
				@Override
				public NBTTagCompound transform(NBTTagCompound input) {
					NBTTagCompound spawndata = input.getCompound("SpawnData");
					input.remove("SpawnPotentials");
					input.remove("SpawnData");
					if (spawndata != null) {
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
			4,
			new SpecificTransformer() {
				@Override
				public NBTTagCompound transform(NBTTagCompound input) {
					PacketDataSerializer.transformSkull(input);
					return input;
				}
			},
			ProtocolVersion.getAllBefore(ProtocolVersion.MINECRAFT_1_7_5)
		);
	}

	public static NBTTagCompound transform(int type, ProtocolVersion version, NBTTagCompound compound) {
		EnumMap<ProtocolVersion, SpecificTransformer> map = registry.get(type);
		if (map != null) {
			SpecificTransformer transformer = map.get(version);
			if (transformer != null) {
				return transformer.transform(compound);
			}
		}
		return compound;
	}

}
