package protocolsupport.protocol.packet.middlepacketimpl.clientbound.play.v_1_6;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.legacyremapper.LegacyUtils;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middlepacket.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middlepacketimpl.PacketData;
import protocolsupport.protocol.packet.v_1_6.utils.ChatEncoder;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Chat extends MiddleChat<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_CHAT_ID, version);
		serializer.writeString(ChatEncoder.encode(LegacyUtils.toText(ChatAPI.fromJSON(chatJson))));
		return RecyclableSingletonList.create(serializer);
	}

}
