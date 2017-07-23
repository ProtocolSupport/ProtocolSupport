package protocolsupport.api.chat.components;

public class SelectorComponent extends BaseComponent {

	private final String selector;

	public SelectorComponent(String selector) {
		this.selector = selector;
	}

	@Override
	public String getValue(String locale) {
		return selector;
	}

}
