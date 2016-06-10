package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_7__1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKickDisconnect;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class KickDisconnect extends MiddleKickDisconnect<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_KICK_DISCONNECT_ID, version);
		serializer.writeString(messageJson);
		return RecyclableSingletonList.create(serializer);
	}

}
