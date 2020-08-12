package protocolsupport.protocol.serializer.chat;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;

import protocolsupport.api.TranslationAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.chat.components.TranslateComponent;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;

public class ServerTranslateTranslateComponentContentSerializer implements ComponentContentSerializer<TranslateComponent> {

	protected static final Pattern langFormat = Pattern.compile("\\%(\\d?)\\$?([%s])");

	@Override
	public void serialize(SimpleJsonTreeSerializer<String> serializer, JsonObject json, TranslateComponent component, String locale) {
		String translation = TranslationAPI.getTranslationString(locale, component.getTranslationKey());
		TextComponent rootcomponent = new TextComponent("");
		List<BaseComponent> tlArgs = component.getTranslationArgs();
		Matcher matcher = langFormat.matcher(translation);
		int index = 0;
		int tlArgIndex = 0;
		while (matcher.find()) {
			rootcomponent.addSibling(new TextComponent(translation.substring(index, matcher.start())));
			index = matcher.end();
			if (matcher.group(2).equals("%")) {
				rootcomponent.addSibling(new TextComponent("%"));
			} else if (matcher.group(2).equals("s")) {
				int lTlArgIndex = tlArgIndex;
				if (!matcher.group(1).isEmpty()) {
					lTlArgIndex = Integer.parseInt(matcher.group(1)) - 1;
				}
				if (tlArgIndex < tlArgs.size()) {
					rootcomponent.addSibling(tlArgs.get(lTlArgIndex));
				} else {
					rootcomponent.addSibling(new TextComponent("[Invalid traslation argument index]"));
				}
			}
			tlArgIndex++;
		}
		TextComponentContentSerializer.INSTANCE.serialize(serializer, json, rootcomponent, locale);
	}

}
