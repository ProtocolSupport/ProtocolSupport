package protocolsupport.protocol.typeremapper.pe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.Utils;

public class PESkinModel {

	private static final PESkinModel normal = loadSkinModel("pe/normal_model.json");
	private static final PESkinModel slim = loadSkinModel("pe/slim_model.json");

	public static PESkinModel getSkinModel(boolean isSlim) {
		return isSlim ? slim : normal;
	}

	private final String skinId;
	private final String skinName;
	private final String geometryId;
	private final String geometryData;

	protected PESkinModel(String skinId, String skinName, String geometryId, String geometryData) {
		this.skinId = skinId;
		this.skinName = skinName;
		this.geometryId = geometryId;
		this.geometryData = geometryData;
	}

	public String getSkinId() {
		return skinId;
	}

	public String getSkinName() {
		return skinName;
	}

	public String getGeometryId() {
		return geometryId;
	}

	public String getGeometryData() {
		return geometryData;
	}

	protected static PESkinModel loadSkinModel(String resourcename) {
		JsonElement element = new JsonParser().parse(Utils.getResource(resourcename));
		JsonObject object = JsonUtils.getAsJsonObject(element, "root element");
		return new PESkinModel(
			JsonUtils.getString(object, "skinId"),
			JsonUtils.getString(object, "skinName"),
			JsonUtils.getString(object, "geometryId"),
			Utils.GSON.toJson(JsonUtils.getJsonObject(object, "geometryData"))
		);
	}

}
