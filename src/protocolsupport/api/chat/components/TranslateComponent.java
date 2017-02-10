package protocolsupport.api.chat.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import protocolsupport.protocol.legacyremapper.LegacyChat;
import protocolsupport.protocol.utils.i18n.I18NData;

public class TranslateComponent extends BaseComponent {

	private final String lang;
	private final String translationKey;
	private final List<BaseComponent> args = new ArrayList<>();

	@Deprecated
	public TranslateComponent(String translationKey, Object... values) {
		this.lang = I18NData.DEFAULT_LANG;
		this.translationKey = translationKey;
		this.args.addAll(Lists.transform(Arrays.asList(values), new Function<Object, BaseComponent>() {
			@Override
			public BaseComponent apply(Object v) {
				return v instanceof BaseComponent ? (BaseComponent) v : new TextComponent(v.toString());
			}
		}));
	}

	public TranslateComponent(String translationKey, BaseComponent... values) {
		this(I18NData.DEFAULT_LANG, translationKey, values);
	}

	public TranslateComponent(String lang, String translationKey, BaseComponent... values) {
		this.lang = lang;
		this.translationKey = translationKey;
		this.args.addAll(Arrays.asList(values));
	}

	public String getTranslationKey() {
		return translationKey;
	}

	@Deprecated
	public List<Object> getArgs() {
		return Lists.transform(args, new Function<BaseComponent, Object>() {
			@Override
			public Object apply(BaseComponent v) {
				return v;
			}
		});
	}

	public List<BaseComponent> getTranslationArgs() {
		return args;
	}

	@Override
	public String getValue() {
		return I18NData.i18n(lang, translationKey, Lists.transform(args, new Function<BaseComponent, String>() {
			@Override
			public String apply(BaseComponent v) {
				return LegacyChat.toText(v);
			}
		}).toArray());
	}

}
