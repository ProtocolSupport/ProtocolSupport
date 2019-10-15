package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13;

import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleInventoryHorseOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class InventoryHorseOpen extends MiddleInventoryHorseOpen {

	public InventoryHorseOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN);
		InventoryOpen.writeData(serializer, windowId, "EntityHorse", new TextComponent("Horse"), slots);
		serializer.writeInt(entityId);
		return RecyclableSingletonList.create(serializer);
	}

}
