package protocolsupport.protocol.typeremapper.legacy;

import org.bukkit.entity.EntityType;

import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;

@SuppressWarnings("deprecation")
public class LegacyMonsterEgg {
	
	private static final TObjectIntHashMap<String> toLegacyId = new TObjectIntHashMap<>();
	private static final TIntObjectHashMap<String> fromLegacyId = new TIntObjectHashMap<>();
	static {
		for (EntityType type : EntityType.values()) {
			toLegacyId.put(type.getName(), type.getTypeId());
			toLegacyId.put(MinecraftData.addNamespacePrefix(type.getName()), type.getTypeId());
			fromLegacyId.put(type.getTypeId(), MinecraftData.addNamespacePrefix(type.getName()));
		}
	}

	public static int toLegacyId(String id) {
		return toLegacyId.get(id);
	}

	public static String fromLegacyId(int id) {
		return fromLegacyId.get(id);
	}

}
