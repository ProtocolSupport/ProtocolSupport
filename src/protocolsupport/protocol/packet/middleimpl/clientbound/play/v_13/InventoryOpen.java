package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindow;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindow.LegacyWindowData;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChatJson;
import protocolsupport.protocol.typeremapper.utils.RemappingTable.EnumRemappingTable;
import protocolsupport.protocol.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryOpen extends MiddleInventoryOpen {

	protected final EnumRemappingTable<WindowType> typeRemapper = GenericIdRemapper.INVENTORY.getTable(version);

	public InventoryOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		type = typeRemapper.getRemap(type);
		LegacyWindowData wdata = LegacyWindow.getData(type);

		return RecyclableSingletonList.create(writeData(
			ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_OPEN_ID),
			version, windowId, wdata.getStringId(), LegacyChatJson.convert(version, cache.getAttributesCache().getLocale(), title), wdata.getSlots()
		));
	}

	public static ClientBoundPacketData writeData(ClientBoundPacketData to, ProtocolVersion version, int windowId, String type, BaseComponent title, int slots) {
		to.writeByte(windowId);
		StringSerializer.writeString(to, version, type);
		StringSerializer.writeString(to, version, ChatAPI.toJSON(title));
		to.writeByte(slots);
		return to;
	}

}
