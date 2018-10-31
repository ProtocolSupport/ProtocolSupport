package protocolsupport.protocol.typeremapper.pe;

import java.util.Arrays;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.Utils;

public class PEItems {

	protected static final int combinedPEStoneId = formLegacyCombinedId(1, 0);

	protected static int formLegacyCombinedId(int id, int data) {
		return (id << 16) | data;
	}

	protected static final int[] toPEId = new int[MinecraftData.ITEM_COUNT];
	protected static final Int2IntMap fromPEId = new Int2IntOpenHashMap();
	protected static void register(String modernKey, int legacyMainId, int legacyData) {
		int modernId = ItemMaterialLookup.getRuntimeId(ItemMaterialLookup.getByKey(modernKey));
		int combinedLegacyId = formLegacyCombinedId(legacyMainId, legacyData);

		if(modernId == -1)
			throw new IllegalArgumentException("modernId missing for modernKey " + modernKey);

		toPEId[modernId] = combinedLegacyId;
		fromPEId.put(combinedLegacyId, modernId);
	}
	static {
		Arrays.fill(toPEId, combinedPEStoneId);
		for (JsonElement element : Utils.iterateJsonArrayResource(PEDataValues.getResourcePath("itemmapping.json"))) {
			JsonObject object = element.getAsJsonObject();
			register(JsonUtils.getString(object, "itemkey"), JsonUtils.getInt(object, "peid"), JsonUtils.getInt(object, "pedata"));
		}
	}

	public static int getIdFromPECombinedId(int PEId) {
		return PEId >>> 16;
	}

	public static int getDataFromPECombinedId(int PEId) {
		return PEId & 0xFFFF;
	}

	public static int getPECombinedIdByModernId(int modernId) {
		return toPEId[modernId];
	}

	public static int getModernIdByPECombinedId(int PECombinedId) {
		return fromPEId.getOrDefault(PECombinedId, combinedPEStoneId);
	}

	public static int getModernIdByPEIdData(int PEId, int PEData) {
		return fromPEId.getOrDefault(formLegacyCombinedId(PEId, PEData), fromPEId.getOrDefault(formLegacyCombinedId(PEId, 0), combinedPEStoneId));
	}

}
