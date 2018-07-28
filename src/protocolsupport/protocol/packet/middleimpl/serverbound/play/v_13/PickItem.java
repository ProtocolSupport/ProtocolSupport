package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddlePickItem;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class PickItem extends MiddlePickItem {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		slot = VarNumberSerializer.readVarInt(clientdata);
	}

}
