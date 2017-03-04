package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleStatistics extends ClientBoundMiddlePacket {

	protected Statistic[] statistics;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		statistics = new Statistic[VarNumberSerializer.readVarInt(serverdata)];
		for (int i = 0; i < statistics.length; i++) {
			Statistic stat = new Statistic();
			stat.name = StringSerializer.readString(serverdata, ProtocolVersion.getLatest());
			stat.value = VarNumberSerializer.readVarInt(serverdata);
			statistics[i] = stat;
		}
	}

	protected static class Statistic {
		public String name;
		public int value;
	}

}
