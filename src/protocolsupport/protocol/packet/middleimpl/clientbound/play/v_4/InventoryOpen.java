package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.basic.GenericIdRemapper;
import protocolsupport.utils.Utils;
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
		serializer.writeByte(GenericIdRemapper.INVENTORY.getTable(version).getRemap(type).toLegacyId());
		StringSerializer.writeString(serializer, version, Utils.clampString(title.toLegacyText(cache.getAttributesCache().getLocale()), 32));
		serializer.writeByte(slots);
		return RecyclableSingletonList.create(serializer);
	}

}
