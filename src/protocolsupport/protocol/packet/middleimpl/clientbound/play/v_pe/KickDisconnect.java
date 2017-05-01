package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKickDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class KickDisconnect extends MiddleKickDisconnect {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		return create(version, messageJson);
	}

	public static RecyclableCollection<ClientBoundPacketData> create(ProtocolVersion version, String messageJson) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.DISCONNECT, version);
		serializer.writeBoolean(false); //do not hide disconnection screen
		StringSerializer.writeString(serializer, version, ChatAPI.fromJSON(messageJson).toLegacyText());
		return RecyclableSingletonList.create(serializer);
	}

}
