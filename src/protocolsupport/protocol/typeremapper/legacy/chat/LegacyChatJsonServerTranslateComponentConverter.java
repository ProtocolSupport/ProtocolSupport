package protocolsupport.protocol.typeremapper.legacy.chat;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.TranslationAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.chat.components.TranslateComponent;

public class LegacyChatJsonServerTranslateComponentConverter extends LegacyChatJsonComponentConverter {

	protected static final Pattern langFormat = Pattern.compile("\\%(\\d?)\\$?([%s])");

	@Override
	public BaseComponent convert(ProtocolVersion version, String locale, BaseComponent component) {
		if (component instanceof TranslateComponent) {
			TranslateComponent tcomponent = (TranslateComponent) component;
			List<BaseComponent> tlargs = tcomponent.getTranslationArgs();
			BaseComponent rootcomponent = cloneComponentAuxData(tcomponent, new TextComponent(""));
			String translation = TranslationAPI.getTranslationString(locale, tcomponent.getTranslationKey());
			Matcher matcher = langFormat.matcher(translation);
			int index = 0;
			int tlargindex = 0;
			while (matcher.find()) {
				rootcomponent.addSibling(new TextComponent(translation.substring(index, matcher.start())));
				index = matcher.end();
				if (matcher.group(2).equals("%")) {
					rootcomponent.addSibling(new TextComponent("%"));
				} else if (matcher.group(2).equals("s")) {
					int ltlargindex = tlargindex;
					if (!matcher.group(1).isEmpty()) {
						ltlargindex = Integer.parseInt(matcher.group(1)) - 1;
					}
					if (tlargindex < tlargs.size()) {
						rootcomponent.addSibling(tlargs.get(ltlargindex));
					} else {
						rootcomponent.addSibling(new TextComponent("[Invalid traslation argument index]"));
					}
				}
				tlargindex++;
			}
			rootcomponent.addSibling(new TextComponent(translation.substring(index)));
			return rootcomponent;
		}
		return component;
	}

}
