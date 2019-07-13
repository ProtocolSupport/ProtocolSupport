package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_13_14;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStatistics;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Statistics extends MiddleStatistics {

	public Statistics(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_STATISTICS_ID);
		ArraySerializer.writeVarIntTArray(serializer, statistics, (to, statistic) -> {
			VarNumberSerializer.writeVarInt(to, statistic.category);
			VarNumberSerializer.writeVarInt(to, statistic.id);
			VarNumberSerializer.writeVarInt(to, statistic.value);
		});
		return RecyclableSingletonList.create(serializer);
	}

}
