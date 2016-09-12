package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_6__1_7__1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityAttach;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityAttach extends MiddleEntityAttach<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_ENTITY_ATTACH_ID, version);
		serializer.writeInt(entityId);
		serializer.writeInt(vehicleId);
		serializer.writeBoolean(true);
		return RecyclableSingletonList.create(serializer);
	}

}
