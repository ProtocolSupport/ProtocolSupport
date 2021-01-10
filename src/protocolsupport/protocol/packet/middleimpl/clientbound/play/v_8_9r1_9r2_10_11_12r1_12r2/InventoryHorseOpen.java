package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryHorseOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.utils.i18n.I18NData;

public class InventoryHorseOpen extends MiddleInventoryHorseOpen {

	public InventoryHorseOpen(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData windowhorseopen = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN);
		InventoryOpen.writeData(windowhorseopen, windowId, "EntityHorse", ChatSerializer.serialize(version, I18NData.DEFAULT_LOCALE, new TextComponent("Horse")), slots);
		windowhorseopen.writeInt(entityId);
		codec.writeClientbound(windowhorseopen);
	}

}
