package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_15_16r1_16r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStatistics;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class Statistics extends MiddleStatistics {

	public Statistics(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData statisticsupdate = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_STATISTICS);
		ArraySerializer.writeVarIntTArray(statisticsupdate, statistics, (to, statistic) -> {
			VarNumberSerializer.writeVarInt(to, statistic.category);
			VarNumberSerializer.writeVarInt(to, statistic.id);
			VarNumberSerializer.writeVarInt(to, statistic.value);
		});
		codec.write(statisticsupdate);
	}

}
