package protocolsupport.protocol.typeremapper.itemstack;

import java.util.Arrays;
import java.util.Map.Entry;

import org.bukkit.Material;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftItemData;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@SuppressWarnings("deprecation")
@Preload
public class PreFlatteningItemIdData {

	private PreFlatteningItemIdData() {
	}

	private static final int combinedLegacyStoneId = formLegacyCombinedId(Material.LEGACY_STONE.getId(), 0);
	private static final int[] toLegacyId = new int[MinecraftItemData.ITEM_COUNT];
	private static final Int2IntMap fromLegacyId = new Int2IntOpenHashMap();

	private static void register(int modernId, int legacyId) {
		toLegacyId[modernId] = legacyId;
		fromLegacyId.put(legacyId, modernId);
	}

	static {
		fromLegacyId.defaultReturnValue(-1);

		Arrays.fill(toLegacyId, combinedLegacyStoneId);

		JsonObject rootObject = ResourceUtils.getAsJsonObject(MappingsData.getResourcePath("preflatteningitemid.json"));
		for (Entry<String, JsonElement> itemEntry : rootObject.entrySet()) {
			register(Integer.parseInt(itemEntry.getKey()), itemEntry.getValue().getAsInt());
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
