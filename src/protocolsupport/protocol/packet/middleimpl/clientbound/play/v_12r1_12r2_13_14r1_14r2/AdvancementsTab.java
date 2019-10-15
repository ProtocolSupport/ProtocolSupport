package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_12r1_12r2_13_14r1_14r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleAdvancementsTab;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class AdvancementsTab extends MiddleAdvancementsTab {

	public AdvancementsTab(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<? extends IPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ADVANCEMENTS_TAB);
		if (identifier != null) {
			serializer.writeBoolean(true);
			StringSerializer.writeVarIntUTF8String(serializer, identifier);
		} else {
			serializer.writeBoolean(false);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
