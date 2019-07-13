package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_12r1_12r2_13_14;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleAdvancementsTab;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class AdvancementsTab extends MiddleAdvancementsTab {

	public AdvancementsTab(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ADVANCEMENTS_TAB);
		if (identifier != null) {
			serializer.writeBoolean(true);
			StringSerializer.writeString(serializer, version, identifier);
		} else {
			serializer.writeBoolean(false);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
