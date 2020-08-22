package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType.LegacyWindowData;

public class InventoryOpen extends MiddleInventoryOpen {

	public InventoryOpen(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	@Override
	public void writeToClient0() {
		LegacyWindowData wdata = LegacyWindowType.getData(windowRemapper.toClientWindowType(type));
		ClientBoundPacketData windowopen = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN);
		writeData(
			windowopen,
			windowId, wdata.getStringId(),
			ChatSerializer.serialize(version, clientCache.getLocale(), title),
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
