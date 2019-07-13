package protocolsupport.protocol.typeremapper.itemstack;

import java.util.Arrays;

import org.bukkit.Material;

import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@SuppressWarnings("deprecation")
@Preload
public class PreFlatteningItemIdData {

	protected static final int combinedLegacyStoneId = formLegacyCombinedId(Material.LEGACY_STONE.getId(), 0);
	protected static final int[] toLegacyId = new int[MinecraftData.ITEM_COUNT];
	protected static final Int2IntMap fromLegacyId = new Int2IntOpenHashMap();

	protected static void register(int modernId, int legacyId) {
		toLegacyId[modernId] = legacyId;
		fromLegacyId.put(legacyId, modernId);
	}

	static {
		fromLegacyId.defaultReturnValue(-1);

		Arrays.fill(toLegacyId, combinedLegacyStoneId);

		JsonObject rootObject = ResourceUtils.getAsJson(MappingsData.getResourcePath("preflatteningitemid.json"));
		for (String blockdataString : rootObject.keySet()) {
			register(Integer.parseInt(blockdataString), JsonUtils.getInt(rootObject, blockdataString));
		}
	}

	public static int getLegacyCombinedIdByModernId(int modernId) {
		return toLegacyId[modernId];
	}

	public static int getIdFromLegacyCombinedId(int legacyId) {
		return legacyId >>> 16;
	}

	public static int getDataFromLegacyCombinedId(int legacyId) {
		return legacyId & 0xFFFF;
	}

	public static int getModernIdByLegacyCombinedId(int legacyCombinedId) {
		return fromLegacyId.get(legacyCombinedId);
	}

	public static int getModernIdByLegacyIdData(int legacyId, int data) {
		int modernId = fromLegacyId.get(formLegacyCombinedId(legacyId, data));
		if (modernId != -1) {
			return modernId;
		}
		return fromLegacyId.getOrDefault(formLegacyCombinedId(legacyId, 0), combinedLegacyStoneId);
	}

	protected static int formLegacyCombinedId(int legacyId, int data) {
		return (legacyId << 16) | data;
	}

}
