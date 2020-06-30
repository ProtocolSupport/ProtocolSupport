package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2_15_16;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSelectTrade;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SelectTrade extends MiddleSelectTrade {

	public SelectTrade(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		slot = VarNumberSerializer.readVarInt(clientdata);
	}

}
