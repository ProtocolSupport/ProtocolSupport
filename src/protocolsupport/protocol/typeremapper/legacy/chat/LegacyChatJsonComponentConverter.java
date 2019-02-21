package protocolsupport.protocol.typeremapper.legacy.chat;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;

public abstract class LegacyChatJsonComponentConverter {

	protected static BaseComponent cloneComponentAuxData(BaseComponent from, BaseComponent to) {
		to.addSiblings(from.getSiblings());
		to.setClickAction(from.getClickAction());
		to.setHoverAction(from.getHoverAction());
		to.setClickInsertion(from.getClickInsertion());
		to.setModifier(from.getModifier());
		return to;
	}

	public abstract BaseComponent convert(ProtocolVersion version, String locale, BaseComponent component);

}