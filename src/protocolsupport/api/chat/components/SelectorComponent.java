package protocolsupport.api.chat.components;

/**
 * Chat component that displays the results of an entity selector
 */
public class SelectorComponent extends BaseComponent {

	private final String selector;

	public SelectorComponent(String selector) {
		this.selector = selector;
	}

	@Override
	public String getValue(String locale) {
		return selector;
	}

	@Override
	public SelectorComponent cloneThis() {
		return new SelectorComponent(selector);
	}

}
