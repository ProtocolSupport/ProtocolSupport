package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;
import protocolsupport.protocol.typeremapper.window.WindowTypeIdMappingRegistry;
import protocolsupport.protocol.typeremapper.window.WindowTypeIdMappingRegistry.WindowTypeIdMappingTable;

public class InventoryOpen extends MiddleInventoryOpen {

	public InventoryOpen(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected final WindowTypeIdMappingTable windowTypeIdMappingTable = WindowTypeIdMappingRegistry.INSTANCE.getTable(version);

	@Override
	protected void writeToClient0() {
		ClientBoundPacketData windowopen = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WINDOW_OPEN);
		writeData(
			windowopen,
			version,
			windowId,
			((Number) windowTypeIdMappingTable.get(windowRemapper.toClientWindowType(type))).intValue(),
			title.toLegacyText(clientCache.getLocale()),
			windowRemapper.toClientWindowSlots(0)
		);
		codec.writeClientbound(windowopen);
	}

	public static void writeData(ClientBoundPacketData to, ProtocolVersion version, int windowId, int type, String title, int slots) {
		to.writeByte(windowId);
		to.writeByte(type);
		StringSerializer.writeString(to, version, LegacyChat.clampLegacyText(title, 32));
		to.writeByte(slots);
		to.writeBoolean(true); //use provided title
	}

}
