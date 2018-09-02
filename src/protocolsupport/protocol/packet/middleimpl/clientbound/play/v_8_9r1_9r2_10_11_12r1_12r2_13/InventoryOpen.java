package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyChatJson;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryOpen extends MiddleInventoryOpen {

	public InventoryOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_OPEN_ID);
		serializer.writeByte(windowId);
		StringSerializer.writeString(serializer, version, GenericIdRemapper.INVENTORY.getTable(version).getRemap(type).getId());
		StringSerializer.writeString(serializer, version, ChatAPI.toJSON(LegacyChatJson.convert(version, cache.getAttributesCache().getLocale(), title)));
		serializer.writeByte(slots);
		if (type == WindowType.HORSE) {
			serializer.writeInt(horseId);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
