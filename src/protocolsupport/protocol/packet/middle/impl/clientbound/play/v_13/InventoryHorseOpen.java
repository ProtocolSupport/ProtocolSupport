package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13;

import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2.AbstractStateIdTrackInventoryHorseOpen;
import protocolsupport.protocol.utils.i18n.I18NData;

public class InventoryHorseOpen extends AbstractStateIdTrackInventoryHorseOpen implements IClientboundMiddlePacketV13 {

	public InventoryHorseOpen(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData inventoryopen = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_OPEN);
		InventoryOpen.writeData(inventoryopen, windowId, "EntityHorse", ChatCodec.serialize(version, I18NData.DEFAULT_LOCALE, new TextComponent("Horse")), slots);
		inventoryopen.writeInt(entityId);
		io.writeClientbound(inventoryopen);
	}

}
