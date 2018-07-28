package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleQueryEntityNBT;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class QueryEntityNBT extends MiddleQueryEntityNBT {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		id = VarNumberSerializer.readVarInt(clientdata);
		entityId = VarNumberSerializer.readVarInt(clientdata);
	}

}
