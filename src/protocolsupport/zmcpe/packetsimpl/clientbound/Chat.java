package protocolsupport.zmcpe.packetsimpl.clientbound;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zmcpe.packetsimpl.PEPacketIDs;

public class Chat extends MiddleChat<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHAT, version);
		serializer.writeByte(0); //raw type
		serializer.writeString(ChatAPI.fromJSON(chatJson).toLegacyText());
		return RecyclableSingletonList.create(serializer);
	}

}
