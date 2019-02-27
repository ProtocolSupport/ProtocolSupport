package protocolsupport.api.chat.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import protocolsupport.api.TranslationAPI;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;

/**
 * Chat component that displays translated key and arguments into the current client language <br>
 * Note: ProtocolSupport always translate components serverside, so this component will never be actually sent to client <br>
 * This unfortunately breaks clientside custom translations, but the don't have other way <br>
 * But it is possible to inject custom serverside translation using {@link TranslationAPI#registerTranslation(String, java.io.InputStream)}
 */
public class TranslateComponent extends BaseComponent {

	private final String translationKey;
	private final List<BaseComponent> args = new ArrayList<>();

	public TranslateComponent(String translationKey, BaseComponent... values) {
		this(translationKey, Arrays.asList(values));
	}

	public TranslateComponent(String translationKey, Collection<BaseComponent> values) {
		this.translationKey = translationKey;
		this.args.addAll(values);
	}

	public String getTranslationKey() {
		return translationKey;
	}

	public List<BaseComponent> getTranslationArgs() {
		return Collections.unmodifiableList(args);
	}

	@Override
	public String getValue(String locale) {
		return TranslationAPI.translate(locale, translationKey, Lists.transform(args, v -> LegacyChat.toText(v, locale)).toArray(new String[0]));
	}

}
