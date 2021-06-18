package protocolsupport.protocol.codec.chat;

import com.google.gson.JsonObject;

import protocolsupport.api.chat.components.ScoreComponent;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;

public class ScoreComponentContentSerializer implements ComponentContentSerializer<ScoreComponent> {

	public static final ScoreComponentContentSerializer INSTANCE = new ScoreComponentContentSerializer();

	protected ScoreComponentContentSerializer() {
	}

	@Override
	public void serialize(SimpleJsonTreeSerializer<String> serializer, JsonObject json, ScoreComponent component, String locale) {
		JsonObject scoreJSON = new JsonObject();
		scoreJSON.addProperty("name", component.getPlayerName());
		scoreJSON.addProperty("objective", component.getObjectiveName());
		if (component.hasValue()) {
			scoreJSON.addProperty("value", component.getValue());
		}
		json.add("score", scoreJSON);
	}

}
