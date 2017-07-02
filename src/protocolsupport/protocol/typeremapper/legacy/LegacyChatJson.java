package protocolsupport.protocol.typeremapper.legacy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.KeybindComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.chat.components.TranslateComponent;
import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.minecraftdata.ItemData;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class LegacyChatJson {

	public static BaseComponent convert(BaseComponent message, ProtocolVersion version, String locale) {
		return replaceComponent(fixSingleComponent(message, version, locale), version, locale);
	}

	private static final EnumMap<ProtocolVersion, List<ComponentConverter>> registry = new EnumMap<>(ProtocolVersion.class);

	private static void register(ComponentConverter r, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			Utils.getFromMapOrCreateDefault(registry, version, new ArrayList<>()).add(r);
		}
	}

	static {
		register((version, locale, component) -> {
			ClickAction click = component.getClickAction();
			if ((click != null) && (click.getType() == ClickAction.Type.OPEN_URL)) {
				String url = click.getValue();
				if (!url.startsWith("http://") && !url.startsWith("https://")) {
					url = "http://"+url;
				}
				component.setClickAction(new ClickAction(ClickAction.Type.OPEN_URL, url));
			}
			return component;
		}, ProtocolVersion.getAllSupported());
		register((version, locale, component) -> {
			if (component instanceof TranslateComponent) {
				TranslateComponent tcomponent = (TranslateComponent) component;
				if (!LegacyI18NData.isSupported(tcomponent.getTranslationKey(), version)) {
					return new TextComponent(tcomponent.getValue(locale));
				}
			}
			return component;
		}, ProtocolVersion.getAllSupported());
		register((version, locale, component) -> {
			if (component instanceof KeybindComponent) {
				return new TextComponent(component.getValue());
			}
			return component;
		}, ProtocolVersionsHelper.BEFORE_1_12);
		register((version, locale, component) -> {
			HoverAction hover = component.getHoverAction();
			if ((hover != null) && (hover.getType() == HoverAction.Type.SHOW_ITEM)) {
				NBTTagCompoundWrapper compound = ServerPlatform.get().getWrapperFactory().createNBTCompoundFromJson(hover.getValue());
				Integer id = ItemData.getIdByName(compound.getString("id"));
				if (id != null) {
					compound.setInt("id", id);
				}
				component.setHoverAction(new HoverAction(HoverAction.Type.SHOW_ITEM, compound.toString()));
			}
			return component;
		}, ProtocolVersionsHelper.BEFORE_1_8);
	}

	private static BaseComponent fixSingleComponent(BaseComponent message, ProtocolVersion version, String locale) {
		List<BaseComponent> siblings = new ArrayList<>(message.getSiblings());
		message.clearSiblings();
		message.addSiblings(replaceComponents(siblings, version, locale));
		if (message instanceof TranslateComponent) {
			TranslateComponent tlcomp = (TranslateComponent) message;
			message = new TranslateComponent(tlcomp.getTranslationKey(), replaceComponents(tlcomp.getTranslationArgs(), version, locale));
		}
		return message;
	}

	private static List<BaseComponent> replaceComponents(List<BaseComponent> oldlist, ProtocolVersion version, String locale) {
		List<BaseComponent> newlist = new ArrayList<>();
		for (BaseComponent old : oldlist) {
			newlist.add(replaceComponent(fixSingleComponent(old, version, locale), version, locale));
		}
		return newlist;
	}

	private static BaseComponent replaceComponent(BaseComponent old, ProtocolVersion version, String locale) {
		for (ComponentConverter r : registry.getOrDefault(version, Collections.emptyList())) {
			old = r.convert(version, locale, old);
		}
		return old;
	}

	@FunctionalInterface
	private static interface ComponentConverter {
		public BaseComponent convert(ProtocolVersion version, String locale, BaseComponent from);
	}

}
