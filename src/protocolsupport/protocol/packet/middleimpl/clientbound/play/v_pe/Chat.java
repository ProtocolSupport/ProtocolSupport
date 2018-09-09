package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI.MessagePosition;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChat;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Chat extends MiddleChat {

	public Chat(ConnectionImpl connection) {
		super(connection);
	}

	public static final int TYPE_RAW = 0;
	public static final int TYPE_CHAT = 1;
	public static final int TYPE_TRANSLATION = 2;
	public static final int TYPE_POPUP = 3;
	public static final int TYPE_JUKEBOX_POPUP = 4;
	public static final int TYPE_TIP = 5;
	public static final int TYPE_SYSTEM = 6;
	public static final int TYPE_WHISPER = 7;
	public static final int TYPE_ANNOUNCEMENT = 8;

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.CHAT);
		serializer.writeByte(position == MessagePosition.HOTBAR ? TYPE_TIP : TYPE_RAW); //type
		serializer.writeBoolean(false); //isLocalise?
		StringSerializer.writeString(serializer, version, message.toLegacyText(cache.getAttributesCache().getLocale()));
		StringSerializer.writeString(serializer, version, ""); //Xbox user ID
		StringSerializer.writeString(serializer, version, ""); //Platform chat id.
		return RecyclableSingletonList.create(serializer);
	}

}
