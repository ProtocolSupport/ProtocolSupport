package protocolsupport.api.chat.components;

/**
 * Chat component that displays provided text
 */
public class TextComponent extends BaseComponent {

	private final String text;

	public TextComponent(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String getValue(String locale) {
		return text;
	}

	@Override
	public TextComponent cloneThis() {
		return new TextComponent(text);
	}

}
