package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_4;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupport.protocol.utils.types.WindowType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.MiscImplUtils;

public class InventoryOpen extends MiddleInventoryOpen<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		if (IdSkipper.INVENTORY.getTable(version).shouldSkip(invname)) {
			cache.closeWindow();
			connection.receivePacket(MiscImplUtils.createInboundInventoryClosePacket());
			return RecyclableEmptyList.get();
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_OPEN_ID, version);
		serializer.writeByte(windowId);
		serializer.writeByte(WindowType.fromName(IdRemapper.INVENTORY.getTable(version).getRemap(invname)).ordinal());
		serializer.writeString(ChatAPI.fromJSON(titleJson).toLegacyText());
		serializer.writeByte(slots);
		return RecyclableSingletonList.create(serializer);
	}

}
