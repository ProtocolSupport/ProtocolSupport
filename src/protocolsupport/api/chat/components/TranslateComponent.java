package protocolsupport.api.chat.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import protocolsupport.api.TranslationAPI;

/**
 * Chat component that displays text int current client language using key and arguments <br>
 * Note: ProtocolSupport always translate components serverside, so this component will never be actually sent to client <br>
 * This unfortunately breaks clientside custom translations, but it is possible to inject custom serverside translation using {@link TranslationAPI#registerTranslation(String, java.io.InputStream)}
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
		String[] legacyArgs = new String[args.size()];
		for (int i = 0; i < args.size(); i++) {
			legacyArgs[i] = args.get(i).toLegacyText(locale);
		}
		return TranslationAPI.translate(locale, translationKey, legacyArgs);
	}

	@Override
	public TranslateComponent cloneThis() {
		return new TranslateComponent(translationKey, cloneFullList(args));
	}

}
