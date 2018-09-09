package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityLeash;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityLeash extends MiddleEntityLeash {

	public EntityLeash(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ENTITY_LEASH_ID);
		serializer.writeInt(entityId);
		serializer.writeInt(vehicleId);
		serializer.writeBoolean(true);
		return RecyclableSingletonList.create(serializer);
	}

}
