package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Chat extends MiddleChat {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHAT, version);
		serializer.writeByte(position == MessagePosition.HOTBAR ? 5 : 0); //type
		serializer.writeByte(0); //isLocalise?
		StringSerializer.writeString(serializer, version, message.toLegacyText(cache.getLocale()));
		StringSerializer.writeString(serializer, version, ""); //Xbox user ID
		return RecyclableSingletonList.create(serializer);
	}

}
