package protocolsupport.protocol.legacyremapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.KeybindComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.chat.components.TranslateComponent;
import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.data.ItemData;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class LegacyChatJson {

	public static BaseComponent convert(BaseComponent message, ProtocolVersion version) {
		return fixSingleComponent(message, version);
	}

	private static final EnumMap<ProtocolVersion, List<Function<BaseComponent, BaseComponent>>> registry = new EnumMap<>(ProtocolVersion.class);

	private static void register(Function<BaseComponent, BaseComponent> r, ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			Utils.getFromMapOrCreateDefault(registry, version, new ArrayList<>()).add(r);
		}
	}

	static {
		register(component -> {
			ClickAction click = component.getClickAction();
			if ((click != null) && (click.getType() == ClickAction.Type.OPEN_URL)) {
				String url = click.getValue();
				if (!url.startsWith("http://") && !url.startsWith("https://")) {
					url = "http://"+url;
				}
				component.setClickAction(new ClickAction(ClickAction.Type.OPEN_URL, url));
			}
			return component;
		}, ProtocolVersion.values());
		register(component -> {
			if (component instanceof KeybindComponent) {
				return new TextComponent(component.getValue());
			}
			return component;
		}, ProtocolVersionsHelper.BEFORE_1_12);
		register(component -> {
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

	private static BaseComponent fixSingleComponent(BaseComponent message, ProtocolVersion version) {
		List<BaseComponent> siblings = new ArrayList<>(message.getSiblings());
		message.clearSiblings();
		message.addSiblings(replaceComponents(siblings, version));
		if (message instanceof TranslateComponent) {
			TranslateComponent tlcomp = (TranslateComponent) message;
			message = new TranslateComponent(tlcomp.getTranslationKey(), replaceComponents(tlcomp.getTranslationArgs(), version));
		}
		return message;
	}

	private static List<BaseComponent> replaceComponents(List<BaseComponent> oldlist, ProtocolVersion version) {
		List<BaseComponent> newlist = new ArrayList<>();
		List<Function<BaseComponent, BaseComponent>> tlist = registry.getOrDefault(version, Collections.emptyList());
		for (BaseComponent old : oldlist) {
			old = fixSingleComponent(old, version);
			for (Function<BaseComponent, BaseComponent> r : tlist) {
				old = r.apply(old);
			}
			newlist.add(old);
		}
		return newlist;
	}

}
