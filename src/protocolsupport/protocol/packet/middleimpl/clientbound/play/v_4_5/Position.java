package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddlePosition;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Position extends MiddlePosition {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_POSITION_ID, version);
		serializer.writeDouble(x);
		serializer.writeDouble(y + 1.6200000047683716D);
		serializer.writeDouble(y);
		serializer.writeDouble(z);
		serializer.writeFloat(yaw);
		serializer.writeFloat(pitch);
		serializer.writeBoolean(false);
		return RecyclableSingletonList.create(serializer);
	}

}
