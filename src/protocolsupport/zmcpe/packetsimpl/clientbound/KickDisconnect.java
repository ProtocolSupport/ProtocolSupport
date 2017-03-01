package protocolsupport.zmcpe.packetsimpl.clientbound;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKickDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zmcpe.packetsimpl.PEPacketIDs;

public class KickDisconnect extends MiddleKickDisconnect<RecyclableCollection<ClientBoundPacketData>> {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.DISCONNECT, version);
		serializer.writeBoolean(false); //do not hide disconnection screen
		serializer.writeString(ChatAPI.fromJSON(messageJson).toLegacyText());
		return RecyclableSingletonList.create(serializer);
	}

}
