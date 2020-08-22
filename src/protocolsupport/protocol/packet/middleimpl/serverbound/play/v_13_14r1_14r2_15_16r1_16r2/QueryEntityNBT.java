package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleQueryEntityNBT;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class QueryEntityNBT extends MiddleQueryEntityNBT {

	public QueryEntityNBT(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		id = VarNumberSerializer.readVarInt(clientdata);
		entityId = VarNumberSerializer.readVarInt(clientdata);
	}

}
