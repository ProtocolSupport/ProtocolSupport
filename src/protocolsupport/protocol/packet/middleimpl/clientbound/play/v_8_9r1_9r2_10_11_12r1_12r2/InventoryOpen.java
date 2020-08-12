package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType.LegacyWindowData;
import protocolsupport.protocol.utils.i18n.I18NData;

public class InventoryOpen extends MiddleInventoryOpen {

	public InventoryOpen(ConnectionImpl connection) {
		super(connection);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	protected void writeToClient0() {
		LegacyWindowData wdata = LegacyWindowType.getData(windowRemapper.toClientWindowType(type));
		ClientBoundPacketData windowopen = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN);
		writeData(
			windowopen,
			windowId, wdata.getStringId(),
			ChatSerializer.serialize(version, I18NData.DEFAULT_LOCALE, new TextComponent(title.toLegacyText(clientCache.getLocale()))),
			windowRemapper.toClientSlots(0)
		);
		codec.write(windowopen);
	}

	public static void writeData(ClientBoundPacketData to, int windowId, String type, String titleJson, int slots) {
		to.writeByte(windowId);
		StringSerializer.writeVarIntUTF8String(to, type);
		StringSerializer.writeVarIntUTF8String(to, titleJson);
		to.writeByte(slots);
	}

}
