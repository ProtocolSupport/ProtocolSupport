package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_12r1_12r2;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleAdvancementsTab;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class AdvancementsTab extends MiddleAdvancementsTab {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ADVANCEMENTS_TAB, version);
		if (identifier != null) {
			serializer.writeBoolean(true);
			StringSerializer.writeString(serializer, version, identifier);
		} else {
			serializer.writeBoolean(false);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
