package protocolsupport.protocol.typeremapper.legacy.chat;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatColor;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.modifiers.Modifier;

public class LegacyChatJsonModifierColorConverter extends LegacyChatJsonComponentConverter {

	@Override
	public BaseComponent convert(ProtocolVersion version, String locale, BaseComponent component) {
		Modifier modifier = component.getModifier();
		if (modifier.hasColor()) {
			ChatColor color = modifier.getRGBColor();
			if (!color.isBasic()) {
				modifier.setRGBColor(color.asBasic());
			}
		}
		return component;
	}

}
