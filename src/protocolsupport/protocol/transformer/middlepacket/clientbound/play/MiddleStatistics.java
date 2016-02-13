package protocolsupport.protocol.transformer.middlepacket.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.middlepacket.ClientBoundMiddlePacket;

public abstract class MiddleStatistics<T> extends ClientBoundMiddlePacket<T> {

	protected Statistic[] statistics;

	@Override
	public void readFromServerData(PacketDataSerializer serializer) throws IOException {
		statistics = new Statistic[serializer.readVarInt()];
		for (int i = 0; i < statistics.length; i++) {
			Statistic stat = new Statistic();
			stat.name = serializer.readString(Short.MAX_VALUE);
			stat.value = serializer.readVarInt();
			statistics[i] = stat;
		}
	}

	protected static class Statistic {
		public String name;
		public int value;
	}

}
