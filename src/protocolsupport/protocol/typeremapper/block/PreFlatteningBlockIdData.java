package protocolsupport.protocol.typeremapper.block;

import java.util.Arrays;

import org.bukkit.Material;

import com.google.gson.JsonObject;

import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.ResourceUtils;
import protocolsupportbuildprocessor.Preload;

@SuppressWarnings("deprecation")
@Preload
public class PreFlatteningBlockIdData {

	protected static final int[] toLegacyId = new int[MinecraftData.BLOCKDATA_COUNT];

	static {
		Arrays.fill(toLegacyId, Material.LEGACY_BEDROCK.getId() << 4);

		JsonObject rootObject = ResourceUtils.getAsJson(MappingsData.getResourcePath("preflatteningblockdataid.json"));
		for (String blockdataString : rootObject.keySet()) {
			toLegacyId[Integer.parseInt(blockdataString)] = JsonUtils.getInt(rootObject, blockdataString);
		}
	}

	/**
	 * Returns the legacy block id + data combined into int <br>
	 * Currently the way how it is combined is the same as 1.8 combined id format
	 * @param modernId modern block id
	 * @return legacy block id + data combined into int
	 */
	public static int getCombinedId(int modernId) {
		return toLegacyId[modernId];
	}

	/**
	 * Extracts block id from legacy combined id (returned by {@link PreFlatteningBlockIdData#getCombinedId(int)})
	 * @param legacyId legacy combined block id
	 * @return block id
	 */
	public static int getIdFromCombinedId(int legacyId) {
		return legacyId >> 4;
	}

	/**
	 * Extracts data from legacy combined id (returned by {@link PreFlatteningBlockIdData#getCombinedId(int)})
	 * @param legacyId legacy combined block id
	 * @return data
	 */
	public static int getDataFromCombinedId(int legacyId) {
		return legacyId & 0xF;
	}

	/**
	 * Converts legacy combined block id (returned by {@link PreFlatteningBlockIdData#getCombinedId(int)}) to another combined id <br>
	 * This combined id is encoded as follows: ((block id) | (data << 12)) <br>
	 * @param legacyId legacy combined block id
	 * @return combined id in another form
	 */
	public static int convertCombinedIdToM12(int legacyId) {
		return (getIdFromCombinedId(legacyId)) | (getDataFromCombinedId(legacyId) << 12);
	}

	/**
	 * Converts legacy combined block id (returned by {@link PreFlatteningBlockIdData#getCombinedId(int)}) to another combined id <br>
	 * This combined id is encoded as follows: ((block id) | (data << 16)) <br>
	 * @param legacyId legacy combined block id
	 * @return combined id in another form
	 */
	public static int convertCombinedIdToM16(int legacyId) {
		return (getIdFromCombinedId(legacyId)) | (getDataFromCombinedId(legacyId) << 16);
	}

}
