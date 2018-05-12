package protocolsupport.api.chat.components;

/**
 * Chat component that displays the results of an entity selector<br>
 * Should not be sent to clients
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

}
