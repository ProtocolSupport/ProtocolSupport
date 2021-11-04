package protocolsupport.protocol.packet.middle.impl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.serverbound.play.MiddleQueryBlockNBT;
import protocolsupport.protocol.packet.middle.impl.serverbound.IServerboundMiddlePacketV13;

public class QueryBlockNBT extends MiddleQueryBlockNBT implements IServerboundMiddlePacketV13 {

	public QueryBlockNBT(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		id = VarNumberCodec.readVarInt(clientdata);
		PositionCodec.readPositionLXYZ(clientdata, position);
	}

}
