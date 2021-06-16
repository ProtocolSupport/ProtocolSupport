package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_15_16r1_16r2_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStatistics;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class Statistics extends MiddleStatistics {

	public Statistics(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData statisticsupdate = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_STATISTICS);
		ArraySerializer.writeVarIntTArray(statisticsupdate, statistics, (to, statistic) -> {
			VarNumberSerializer.writeVarInt(to, statistic.category);
			VarNumberSerializer.writeVarInt(to, statistic.id);
			VarNumberSerializer.writeVarInt(to, statistic.value);
		});
		codec.writeClientbound(statisticsupdate);
	}

}
