package protocolsupport.protocol.typeremapper.legacy.chat;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TranslateComponent;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class LegacyChatJson {

	public static BaseComponent convert(ProtocolVersion version, String locale, BaseComponent message) {
		List<BaseComponent> siblings = new ArrayList<>(message.getSiblings());
		message.clearSiblings();
		HoverAction hoveraction = message.getHoverAction();
		if ((hoveraction != null) && (hoveraction.getType() == HoverAction.Type.SHOW_TEXT)) {
			message.setHoverAction(new HoverAction(convert(version, locale, hoveraction.getText())));
		}
		message.addSiblings(convertComponents(siblings, version, locale));
		if (message instanceof TranslateComponent) {
			TranslateComponent tlcomp = (TranslateComponent) message;
			message = LegacyChatJsonComponentConverter.cloneComponentAuxData(tlcomp, new TranslateComponent(tlcomp.getTranslationKey(), convertComponents(tlcomp.getTranslationArgs(), version, locale)));
		}
		for (LegacyChatJsonComponentConverter r : registry.getOrDefault(version, Collections.emptyList())) {
			message = r.convert(version, locale, message);
		}
		return message;
	}

	protected static List<BaseComponent> convertComponents(List<BaseComponent> oldlist, ProtocolVersion version, String locale) {
		List<BaseComponent> newlist = new ArrayList<>();
		for (BaseComponent old : oldlist) {
			newlist.add(convert(version, locale, old));
		}
		return newlist;
	}

	protected static final Map<ProtocolVersion, List<LegacyChatJsonComponentConverter>> registry = new EnumMap<>(ProtocolVersion.class);
	static {
		for (ProtocolVersion version : ProtocolVersion.getAllSupported()) {
			registry.put(version, new ArrayList<>());
		}
	}

	protected static void register(LegacyChatJsonComponentConverter r, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			registry.computeIfAbsent(version, k -> {
				throw new IllegalArgumentException(MessageFormat.format("Missing remapping table for version {0}", k));
			}).add(r);
		}
	}

	static {
		register(new LegacyChatJsonURLFixerComponentConverter(), ProtocolVersion.getAllSupported());
		register(new LegacyChatJsonServerTranslateComponentConverter(), ProtocolVersion.getAllSupported());
		register(new LegacyChatJsonKeybindComponentConverter(), ProtocolVersionsHelper.BEFORE_1_12);
		register(new LegacyChatJsonLegacyHoverComponentConverter(), ProtocolVersion.getAllSupported());
	}

}
