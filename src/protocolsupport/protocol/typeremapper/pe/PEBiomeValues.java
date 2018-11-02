package protocolsupport.protocol.typeremapper.pe;

import com.google.gson.JsonObject;
import org.bukkit.block.Biome;
import protocolsupport.utils.Utils;

public class PEBiomeValues {
	static private final int[] pcToPeMap = new int[256];

	static public final int defaultBiomeId = 0; // default to ocean

	static {
		JsonObject mappingObj = Utils.getResourceJson(PEDataValues.getResourcePath("biomeids.json"));

		for(String key : mappingObj.keySet()) {
			JsonObject obj = mappingObj.getAsJsonObject(key);

			final int pcId = obj.get("pc").getAsInt();
			final int peId = obj.get("pe").getAsInt();

			pcToPeMap[pcId] = peId == -1 ? defaultBiomeId : peId;
		}
	}

	static public int pcToPe(int pcId) {
		return pcToPeMap[pcId];
	}
}
