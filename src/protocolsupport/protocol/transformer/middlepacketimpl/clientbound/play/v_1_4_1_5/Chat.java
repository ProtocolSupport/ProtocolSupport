package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_4_1_5;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleChat;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Chat extends MiddleChat<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_CHAT_ID, version);
		serializer.writeString(LegacyUtils.toText(ChatAPI.fromJSON(chatJson)));
		return RecyclableSingletonList.create(serializer);
	}

}
