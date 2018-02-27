package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyChatJson;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class LoginDisconnect extends MiddleLoginDisconnect {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.LOGIN_DISCONNECT_ID, version);
		StringSerializer.writeString(serializer, version, ChatAPI.toJSON(LegacyChatJson.convert(message, version, cache.getAttributesCache().getLocale())));
		return RecyclableSingletonList.create(serializer);
	}

}
