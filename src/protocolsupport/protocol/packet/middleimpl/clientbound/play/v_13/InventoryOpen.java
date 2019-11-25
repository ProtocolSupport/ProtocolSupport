package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType.LegacyWindowData;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChatJson;

public class InventoryOpen extends MiddleInventoryOpen {

	public InventoryOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient0() {
		LegacyWindowData wdata = LegacyWindowType.getData(windowRemapper.toClientWindowType(type));
		ClientBoundPacketData windowopen = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN);
		writeData(
			windowopen,
			windowId, wdata.getStringId(),
			LegacyChatJson.convert(version, cache.getAttributesCache().getLocale(), title),
			windowRemapper.toClientSlots(0)
		);
		codec.write(windowopen);
	}

	public static void writeData(ClientBoundPacketData to, int windowId, String type, BaseComponent title, int slots) {
		to.writeByte(windowId);
		StringSerializer.writeVarIntUTF8String(to, type);
		StringSerializer.writeVarIntUTF8String(to, ChatAPI.toJSON(title));
		to.writeByte(slots);
	}

}
