package protocolsupport.api.chat.components;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_10_R1.LocaleI18n;

public class TranslateComponent extends BaseComponent {

	private String translationKey;
	private List<Object> args = new ArrayList<Object>();

	public TranslateComponent(String translationKey, Object... values) {
		this.translationKey = translationKey;
		for (Object v : values) {
			this.args.add(v);
		}
	}

	public String getTranslationKey() {
		return translationKey;
	}

	public List<Object> getArgs() {
		return args;
	}

	@Override
	public String getValue() {
		return LocaleI18n.a(translationKey, args.toArray());
	}

}
