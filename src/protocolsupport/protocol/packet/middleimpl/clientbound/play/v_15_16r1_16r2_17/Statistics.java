package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_15_16r1_16r2_17;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStatistics;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class Statistics extends MiddleStatistics {

	public Statistics(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData statisticsupdate = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_STATISTICS);
		ArrayCodec.writeVarIntTArray(statisticsupdate, statistics, (to, statistic) -> {
			VarNumberCodec.writeVarInt(to, statistic.category);
			VarNumberCodec.writeVarInt(to, statistic.id);
			VarNumberCodec.writeVarInt(to, statistic.value);
		});
		codec.writeClientbound(statisticsupdate);
	}

}
