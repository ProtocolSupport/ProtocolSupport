package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleStatistics<T> extends ClientBoundMiddlePacket<T> {

	protected Statistic[] statistics;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		statistics = new Statistic[serializer.readVarInt()];
		for (int i = 0; i < statistics.length; i++) {
			Statistic stat = new Statistic();
			stat.name = serializer.readString();
			stat.value = serializer.readVarInt();
			statistics[i] = stat;
		}
	}

	protected static class Statistic {
		public String name;
		public int value;
	}

}
