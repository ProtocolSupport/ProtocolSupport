package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType.LegacyWindowData;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;

public class InventoryOpen extends MiddleInventoryOpen {

	public InventoryOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient0() {
		LegacyWindowData wdata = LegacyWindowType.getData(windowRemapper.toClientWindowType(type));
		ClientBoundPacketData windowopen = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN);
		windowopen.writeByte(windowId);
		windowopen.writeByte(wdata.getIntId());
		StringSerializer.writeShortUTF16BEString(windowopen, LegacyChat.clampLegacyText(title.toLegacyText(cache.getAttributesCache().getLocale()), 32));
		windowopen.writeByte(windowRemapper.toClientSlots(0));
		codec.write(windowopen);
	}

}
