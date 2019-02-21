package protocolsupport.protocol.typeremapper.legacy.chat;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.KeybindComponent;
import protocolsupport.api.chat.components.TextComponent;

public class LegacyChatJsonKeybindComponentConverter extends LegacyChatJsonComponentConverter {

	@Override
	public BaseComponent convert(ProtocolVersion version, String locale, BaseComponent component) {
		if (component instanceof KeybindComponent) {
			return cloneComponentAuxData(component, new TextComponent(component.getValue()));
		}
		return component;
	}

}
