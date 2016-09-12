package protocolsupport.api.chat.components;

public class SelectorComponent extends BaseComponent {

	private String selector;

	public SelectorComponent(String selector) {
		this.selector = selector;
	}

	@Override
	public String getValue() {
		return selector;
	}

}
