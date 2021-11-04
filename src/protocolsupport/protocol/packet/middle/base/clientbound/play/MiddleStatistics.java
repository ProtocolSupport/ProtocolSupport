package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleStatistics extends ClientBoundMiddlePacket {

	protected MiddleStatistics(IMiddlePacketInit init) {
		super(init);
	}

	protected Statistic[] statistics;

	@Override
	protected void decode(ByteBuf serverdata) {
		statistics = ArrayCodec.readVarIntTArray(
			serverdata, Statistic.class,
			from -> new Statistic(VarNumberCodec.readVarInt(from), VarNumberCodec.readVarInt(from), VarNumberCodec.readVarInt(from))
		);
	}

	@Override
	protected void cleanup() {
		statistics = null;
	}

	public static class Statistic {
		public final int category;
		public final int id;
		public final int value;
		public Statistic(int category, int id, int value) {
			this.category = category;
			this.id = id;
			this.value = value;
		}
	}

}
