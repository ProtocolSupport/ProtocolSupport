package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleQueryBlockNBT;

public class QueryBlockNBT extends MiddleQueryBlockNBT {

	public QueryBlockNBT(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		id = VarNumberCodec.readVarInt(clientdata);
		PositionCodec.readPosition(clientdata, position);
	}

}
