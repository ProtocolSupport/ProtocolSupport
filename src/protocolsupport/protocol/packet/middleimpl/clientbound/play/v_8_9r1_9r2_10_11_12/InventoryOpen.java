package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.ServerPlatform;

public class InventoryOpen extends MiddleInventoryOpen {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		if (IdSkipper.INVENTORY.getTable(version).shouldSkip(invname)) {
			cache.closeWindow();
			connection.receivePacket(ServerPlatform.get().getPacketFactory().createInboundInventoryClosePacket());
			return RecyclableEmptyList.get();
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WINDOW_OPEN_ID, version);
		serializer.writeByte(windowId);
		StringSerializer.writeString(serializer, version, IdRemapper.INVENTORY.getTable(version).getRemap(invname));
		StringSerializer.writeString(serializer, version, titleJson);
		serializer.writeByte(slots);
		if (invname.equals("EntityHorse")) {
			serializer.writeInt(horseId);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
