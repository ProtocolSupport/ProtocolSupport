package protocolsupport.protocol.packet.middlepacketimpl.clientbound.login.v_1_4_1_5_1_6;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.legacyremapper.LegacyUtils;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middlepacket.clientbound.login.MiddleLoginDisconnect;
import protocolsupport.protocol.packet.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class LoginDisconnect extends MiddleLoginDisconnect<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.LOGIN_DISCONNECT_ID, version);
		serializer.writeString(LegacyUtils.toText(ChatAPI.fromJSON(messageJson)));
		return RecyclableSingletonList.create(serializer);
	}

}
