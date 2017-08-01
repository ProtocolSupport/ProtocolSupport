package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7;

import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUseBed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class UseBed extends MiddleUseBed {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_BED_ID, connection.getVersion());
		serializer.writeInt(entityId);
		PositionSerializer.writeLegacyPositionB(serializer, bed);
		return RecyclableSingletonList.create(serializer);
	}

}
