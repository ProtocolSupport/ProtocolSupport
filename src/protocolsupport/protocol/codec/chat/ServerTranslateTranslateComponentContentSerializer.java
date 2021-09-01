package protocolsupport.protocol.codec.chat;

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

	public static final ServerTranslateTranslateComponentContentSerializer INSTANCE = new ServerTranslateTranslateComponentContentSerializer();

	protected static final Pattern langFormat = Pattern.compile("\\%(\\d?)\\$?([%s])");

	@Override
	public void serialize(SimpleJsonTreeSerializer<String> serializer, JsonObject json, TranslateComponent component, String locale) {
		String translation = TranslationAPI.getTranslationStringIfExists(locale, component.getTranslationKey());
		if (translation != null) {
			TextComponent translatedComponent = new TextComponent("");
			List<BaseComponent> translationArgs = component.getTranslationArgs();
			Matcher matcher = langFormat.matcher(translation);
			int matchEndIndex = 0;
			int translationArgIndex = 0;
			while (matcher.find()) {
				translatedComponent.addSibling(new TextComponent(translation.substring(matchEndIndex, matcher.start())));
				matchEndIndex = matcher.end();
				String group2 = matcher.group(2);
				if (group2.equals("%")) {
					translatedComponent.addSibling(new TextComponent("%"));
				} else if (group2.equals("s")) {
					int lTranslationArgIndex = translationArgIndex;
					String group1 = matcher.group(1);
					if (!group1.isEmpty()) {
						try {
							lTranslationArgIndex = Integer.parseInt(group1) - 1;
						} catch (NumberFormatException e) {
							lTranslationArgIndex = -1;
						}
					}
					if ((lTranslationArgIndex >= 0) && (lTranslationArgIndex < translationArgs.size())) {
						translatedComponent.addSibling(translationArgs.get(lTranslationArgIndex));
					}
				}
				translationArgIndex++;
			}
			if (matchEndIndex < translation.length()) {
				translatedComponent.addSibling(new TextComponent(translation.substring(matchEndIndex)));
			}
			translatedComponent.addSiblings(component.getSiblings());
			ComponentSerializer.serializeAndAdd(serializer, json, translatedComponent, locale);
		} else {
			TranslateComponentContentSerializer.INSTANCE.serialize(serializer, json, component, locale);
		}
	}

}
