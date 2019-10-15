package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_5_6_7;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType;
import protocolsupport.protocol.typeremapper.legacy.LegacyWindowType.LegacyWindowData;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryOpen extends MiddleInventoryOpen {

	public InventoryOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData0() {
		LegacyWindowData wdata = LegacyWindowType.getData(windowRemapper.toClientWindowType(type));
		return RecyclableSingletonList.create(writeData(
			ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN),
			version, windowId, wdata.getIntId(), title.toLegacyText(cache.getAttributesCache().getLocale()), windowRemapper.toClientSlots(0)
		));
	}

	public static ClientBoundPacketData writeData(ClientBoundPacketData to, ProtocolVersion version, int windowId, int type, String title, int slots) {
		to.writeByte(windowId);
		to.writeByte(type);
		StringSerializer.writeString(to, version, LegacyChat.clampLegacyText(title, 32));
		to.writeByte(slots);
		to.writeBoolean(true); //use provided title
		return to;
	}

}
