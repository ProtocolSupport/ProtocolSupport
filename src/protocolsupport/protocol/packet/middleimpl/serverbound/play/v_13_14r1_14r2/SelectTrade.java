package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSelectTrade;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SelectTrade extends MiddleSelectTrade {

	public SelectTrade(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		slot = VarNumberSerializer.readVarInt(clientdata);
	}

}
