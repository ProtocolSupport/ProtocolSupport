package protocolsupport.protocol.typeremapper.legacy;

import java.util.Arrays;

import org.bukkit.Bukkit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;

public class LegacyBlockId {

	private static final int[] toLegacyId = new int[MinecraftData.ID_MAX];
	private static void register(String modernBlockState, int legacyId, int legacyData) {
		toLegacyId[ServerPlatform.get().getMiscUtils().getNetworkBlockStateId(Bukkit.createBlockData(modernBlockState))] = formLegacyCombinedId(legacyId, legacyData);
	}
	static {
		Arrays.fill(toLegacyId, formLegacyCombinedId(1, 0));
		toLegacyId[0] = 0;
		for (JsonElement element : Utils.iterateJsonArrayResource(MappingsData.getResourcePath("legacyblockid.json"))) {
			JsonObject object = element.getAsJsonObject();
			register(JsonUtils.getString(object, "blockdata"), JsonUtils.getInt(object, "legacyid"), JsonUtils.getInt(object, "legacydata"));
		}
	}

	public static int getLegacyCombinedId(int modernId) {
		return toLegacyId[modernId];
	}

	public static int getIdFromLegacyCombinedId(int legacyId) {
		return legacyId >> 4;
	}

	public static int getDataFromLegacyCombinedId(int oldId) {
		return oldId & 0xF;
	}

	private static int formLegacyCombinedId(int id, int data) {
		return (id << 4) | data;
	}

}
