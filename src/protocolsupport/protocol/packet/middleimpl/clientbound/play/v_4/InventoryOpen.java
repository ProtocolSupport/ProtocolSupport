package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindow;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindow.LegacyWindowData;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
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

		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_OPEN_ID);
		serializer.writeByte(windowId);
		serializer.writeByte(wdata.getIntId());
		StringSerializer.writeString(serializer, version, LegacyChat.clampLegacyText(title.toLegacyText(cache.getAttributesCache().getLocale()), 32));
		serializer.writeByte(wdata.getSlots());
		return RecyclableSingletonList.create(serializer);
	}

}
