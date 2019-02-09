package protocolsupport.protocol.typeremapper.legacy.chat;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.modifiers.ClickAction;

public class LegacyChatJsonURLFixerComponentConverter extends LegacyChatJsonComponentConverter {

	@Override
	public BaseComponent convert(ProtocolVersion version, String locale, BaseComponent component) {
		ClickAction click = component.getClickAction();
		if ((click != null) && (click.getType() == ClickAction.Type.OPEN_URL)) {
			String url = click.getValue();
			if (!url.startsWith("http://") && !url.startsWith("https://")) {
				url = "http://"+url;
			}
			component.setClickAction(new ClickAction(ClickAction.Type.OPEN_URL, url));
		}
		return component;
	}

}
