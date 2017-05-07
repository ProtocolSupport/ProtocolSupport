package protocolsupport.protocol.legacyremapper;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TranslateComponent;
import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.protocol.utils.data.ItemData;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class LegacyChatJson {

	public static BaseComponent convert(BaseComponent message) {
		fixComponent(message);
		if (message instanceof TranslateComponent) {
			for (BaseComponent arg : ((TranslateComponent) message).getTranslationArgs()) {
				convert(arg);
			}
		}
		for (BaseComponent sibling : message.getSiblings()) {
			convert(sibling);
		}
		return message;
	}

	private static void fixComponent(BaseComponent component) {
		HoverAction hover = component.getHoverAction();
		if ((hover != null) && (hover.getType() == HoverAction.Type.SHOW_ITEM)) {
			NBTTagCompoundWrapper compound = ServerPlatform.get().getWrapperFactory().createNBTCompoundFromJson(hover.getValue());
			Integer id = ItemData.getIdByName(compound.getString("id"));
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
