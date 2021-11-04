package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_15_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleStatistics;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;

public class Statistics extends MiddleStatistics implements
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2 {

	public Statistics(IMiddlePacketInit init) {
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
		io.writeClientbound(statisticsupdate);
	}

}
