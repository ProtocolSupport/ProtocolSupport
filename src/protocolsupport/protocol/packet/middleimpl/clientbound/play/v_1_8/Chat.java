package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.packet.v_1_7.utils.ChatJsonConverter;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Chat extends MiddleChat<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_CHAT_ID, version);
		serializer.writeString(ChatJsonConverter.convert(chatJson));
		serializer.writeByte(position);
		return RecyclableSingletonList.create(serializer);
	}

}
