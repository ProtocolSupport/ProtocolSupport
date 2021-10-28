package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2.AbstractStateIdTrackInventoryHorseOpen;
import protocolsupport.protocol.utils.i18n.I18NData;

public class InventoryHorseOpen extends AbstractStateIdTrackInventoryHorseOpen {

	public InventoryHorseOpen(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData inventoryopen = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_OPEN);
		InventoryOpen.writeData(inventoryopen, windowId, "EntityHorse", ChatCodec.serialize(version, I18NData.DEFAULT_LOCALE, new TextComponent("Horse")), slots);
		inventoryopen.writeInt(entityId);
		codec.writeClientbound(inventoryopen);
	}

}
