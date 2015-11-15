package protocolsupport.api.chat.components;

public class TextComponent extends BaseComponent {

	private String text;

	public TextComponent(String text) {
		this.text = text;
	}

	@Override
	public String getValue() {
		return text;
	}

}
