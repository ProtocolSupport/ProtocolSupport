package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSelectTrade;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SelectTrade extends MiddleSelectTrade {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		slot = VarNumberSerializer.readVarInt(clientdata);
	}

}
