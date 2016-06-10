package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_1_4__1_5__1_6;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class LoginDisconnect extends MiddleLoginDisconnect<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.LOGIN_DISCONNECT_ID, version);
		serializer.writeString(ChatAPI.fromJSON(messageJson).toLegacyText());
		return RecyclableSingletonList.create(serializer);
	}

}
