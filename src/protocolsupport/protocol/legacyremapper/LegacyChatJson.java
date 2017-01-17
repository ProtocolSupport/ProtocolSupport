package protocolsupport.protocol.legacyremapper;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TranslateComponent;
import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.zplatform.MiscImplUtils;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class LegacyChatJson {

	public static String convert(String message) {
		BaseComponent component = ChatAPI.fromJSON(message);
		walkComponent(component);
		return ChatAPI.toJSON(component);
	}

	private static void walkComponent(BaseComponent component) {
		fixComponent(component);
		if (component instanceof TranslateComponent) {
			for (BaseComponent arg : ((TranslateComponent) component).getTranslationArgs()) {
				walkComponent(arg);
			}
		}
		for (BaseComponent sibling : component.getSiblings()) {
			walkComponent(sibling);
		}
	}

	private static void fixComponent(BaseComponent component) {
		HoverAction hover = component.getHoverAction();
		if ((hover != null) && (hover.getType() == HoverAction.Type.SHOW_ITEM)) {
			NBTTagCompoundWrapper compound = NBTTagCompoundWrapper.fromJson(hover.getValue());
			Integer id = MiscImplUtils.getItemIdByName(compound.getString("id"));
			if (id != null) {
				compound.setInt("id", id);
			}
			component.setHoverAction(new HoverAction(HoverAction.Type.SHOW_ITEM, compound.toString()));
		}
		ClickAction click = component.getClickAction();
		if ((click != null) && (click.getType() == ClickAction.Type.OPEN_URL)) {
			String url = click.getValue();
			if (!url.startsWith("http://") && !url.startsWith("https://")) {
				url = "http://"+url;
			}
			component.setClickAction(new ClickAction(ClickAction.Type.OPEN_URL, url));
		}
	}

}
