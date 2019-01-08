package protocolsupport.protocol.typeremapper.itemstack;

import java.util.Arrays;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class PreFlatteningItemIdData {

	protected static final int combinedLegacyStoneId = formLegacyCombinedId(1, 0);
	protected static final int[] toLegacyId = new int[MinecraftData.ITEM_COUNT];
	protected static final Int2IntMap fromLegacyId = new Int2IntOpenHashMap();
	protected static void register(String modernKey, int legacyMainId, int legacyData) {
		int modernId = ItemMaterialLookup.getRuntimeId(ItemMaterialLookup.getByKey(modernKey));
		int combinedLegacyId = formLegacyCombinedId(legacyMainId, legacyData);
		toLegacyId[modernId] = combinedLegacyId;
		fromLegacyId.put(combinedLegacyId, modernId);
	}
	static {
		Arrays.fill(toLegacyId, combinedLegacyStoneId);
		for (JsonElement element : ResourceUtils.getAsIterableJson(MappingsData.getResourcePath("preflatteningitemiddata.json"))) {
			JsonObject object = element.getAsJsonObject();
			register(JsonUtils.getString(object, "itemkey"), JsonUtils.getInt(object, "legacyid"), JsonUtils.getInt(object, "legacydata"));
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

	public static int getModernIdByLegacyIdData(int legacyId, int data) {
		return fromLegacyId.getOrDefault(formLegacyCombinedId(legacyId, data), fromLegacyId.getOrDefault(formLegacyCombinedId(legacyId, 0), combinedLegacyStoneId));
	}

	protected static int formLegacyCombinedId(int legacyId, int data) {
		return (legacyId << 16) | data;
	}

}
