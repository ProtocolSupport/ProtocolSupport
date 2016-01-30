package protocolsupport.api.chat.modifiers;

import java.net.MalformedURLException;
import java.net.URL;

public class ClickAction {

	private Type type;
	private String value;

	public ClickAction(Type action, String value) {
		this.type = action;
		this.value = value;
	}

	public ClickAction(URL url) {
		this.type = Type.OPEN_URL;
		this.value = url.toString();
	}

	public Type getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public URL getUrl() throws MalformedURLException {
		if (type == Type.OPEN_URL) {
			return new URL(value);
		}
		throw new IllegalStateException(type + " is not an " + Type.OPEN_URL);
	}

	public static enum Type {
		OPEN_URL, OPEN_FILE, RUN_COMMAND, TWITCH_USER_INFO, SUGGEST_COMMAND, CHANGE_PAGE;
	}

}
