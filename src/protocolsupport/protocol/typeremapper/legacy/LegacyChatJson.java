package protocolsupport.protocol.typeremapper.legacy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.TranslationAPI;
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
		List<BaseComponent> siblings = new ArrayList<>(message.getSiblings());
		message.clearSiblings();
		HoverAction hoveraction = message.getHoverAction();
		if ((hoveraction != null) && (hoveraction.getType() == HoverAction.Type.SHOW_TEXT)) {
			message.setHoverAction(new HoverAction(convert(hoveraction.getText(), version, locale)));
		}
		message.addSiblings(convertComponents(siblings, version, locale));
		if (message instanceof TranslateComponent) {
			TranslateComponent tlcomp = (TranslateComponent) message;
			message = cloneComponentAuxData(tlcomp, new TranslateComponent(tlcomp.getTranslationKey(), convertComponents(tlcomp.getTranslationArgs(), version, locale)));
		}
		for (ComponentConverter r : registry.getOrDefault(version, Collections.emptyList())) {
			message = r.convert(version, locale, message);
		}
		return message;
	}

	private static final EnumMap<ProtocolVersion, List<ComponentConverter>> registry = new EnumMap<>(ProtocolVersion.class);

	private static void register(ComponentConverter r, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			Utils.getFromMapOrCreateDefault(registry, version, new ArrayList<>()).add(r);
		}
	}

	protected static final Pattern langFormat = Pattern.compile("\\%(\\d?)\\$?([%s])");

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
		}, ProtocolVersion.getAllSupported());
		register((version, locale, component) -> {
			if (component instanceof KeybindComponent) {
				return cloneComponentAuxData(component, new TextComponent(component.getValue()));
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

	private static BaseComponent cloneComponentAuxData(BaseComponent from, BaseComponent to) {
		to.addSiblings(from.getSiblings());
		to.setClickAction(from.getClickAction());
		to.setHoverAction(from.getHoverAction());
		to.setClickInsertion(from.getClickInsertion());
		to.setModifier(from.getModifier());
		return to;
	}

	private static List<BaseComponent> convertComponents(List<BaseComponent> oldlist, ProtocolVersion version, String locale) {
		List<BaseComponent> newlist = new ArrayList<>();
		for (BaseComponent old : oldlist) {
			newlist.add(convert(old, version, locale));
		}
		return newlist;
	}

	@FunctionalInterface
	private static interface ComponentConverter {
		public BaseComponent convert(ProtocolVersion version, String locale, BaseComponent from);
	}

}
