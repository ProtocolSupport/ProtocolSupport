package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.api.ProtocolVersion;
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
		writeData(
			windowopen,
			version, windowId, wdata.getIntId(),
			title.toLegacyText(cache.getAttributesCache().getLocale()),
			windowRemapper.toClientSlots(0)
		);
		codec.write(windowopen);
	}

	public static void writeData(ClientBoundPacketData to, ProtocolVersion version, int windowId, int type, String title, int slots) {
		to.writeByte(windowId);
		to.writeByte(type);
		StringSerializer.writeString(to, version, LegacyChat.clampLegacyText(title, 32));
		to.writeByte(slots);
		to.writeBoolean(true); //use provided title
	}

}
