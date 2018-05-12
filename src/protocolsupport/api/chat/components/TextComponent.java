package protocolsupport.api.chat.components;

/**
 * Chat component that displays provided text
 */
public class TextComponent extends BaseComponent {

	private final String text;

	public TextComponent(String text) {
		this.text = text;
	}

	@Override
	public String getValue(String locale) {
		return text;
	}

}
