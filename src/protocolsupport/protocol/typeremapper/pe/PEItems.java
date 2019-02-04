package protocolsupport.protocol.typeremapper.pe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
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

		if (modernId == -1) {
			throw new IllegalArgumentException("modernId missing for modernKey " + modernKey);
		}

		toPEId[modernId] = combinedLegacyId;
		fromPEId.put(combinedLegacyId, modernId);
	}

	static {
		for (JsonElement element : ResourceUtils.getAsIterableJson(PEDataValues.getResourcePath("itemmapping.json"))) {
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
		final int result = toPEId[modernId];

		if (result == 0 && modernId != 0) {
			return combinedPEStoneId;
		}

		return result;
	}

	public static int getModernIdByPEIdData(int PEId, int PEData) {
		final int literalId = formLegacyCombinedId(PEId, PEData);
		final int closestId = formLegacyCombinedId(PEId, 0);

		if (fromPEId.containsKey(literalId)) {
			return fromPEId.get(literalId);
		} else if (fromPEId.containsKey(closestId)) {
			return fromPEId.get(closestId);
		} else {
			System.err.println("Using default item for PE ID " + PEId + ":" + PEData);
			return combinedPEStoneId;
		}
	}

	public static int getModernIdByPECombined(int PEcombined) {
		return fromPEId.get(PEcombined);
	}

}
