package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKickDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class KickDisconnect extends MiddleKickDisconnect {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return create(connection.getVersion(), cache.getLocale(), message);
	}

	public static RecyclableCollection<ClientBoundPacketData> create(ProtocolVersion version, String locale, BaseComponent message) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.DISCONNECT, version);
		serializer.writeBoolean(false); //do not hide disconnection screen
		StringSerializer.writeString(serializer, version, message.toLegacyText(locale));
		return RecyclableSingletonList.create(serializer);
	}

}
