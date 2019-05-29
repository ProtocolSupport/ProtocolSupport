package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleQueryEntityNBT;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class QueryEntityNBT extends MiddleQueryEntityNBT {

	public QueryEntityNBT(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		id = VarNumberSerializer.readVarInt(clientdata);
		entityId = VarNumberSerializer.readVarInt(clientdata);
	}

}
