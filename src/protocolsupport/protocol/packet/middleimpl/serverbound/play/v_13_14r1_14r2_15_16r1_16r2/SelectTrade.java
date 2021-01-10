package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSelectTrade;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SelectTrade extends MiddleSelectTrade {

	public SelectTrade(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		slot = VarNumberSerializer.readVarInt(clientdata);
	}

}
