package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleStatistics extends ClientBoundMiddlePacket {

	protected Statistic[] statistics;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		statistics = ArraySerializer.readVarIntTArray(
			serverdata, Statistic.class,
			(from) -> new Statistic(StringSerializer.readString(from, ProtocolVersion.getLatest(ProtocolType.PC)), VarNumberSerializer.readVarInt(serverdata))
		);
	}

	public static class Statistic {
		public String name;
		public int value;
		public Statistic(String name, int value) {
			this.name = name;
			this.value = value;
		}
	}

}
