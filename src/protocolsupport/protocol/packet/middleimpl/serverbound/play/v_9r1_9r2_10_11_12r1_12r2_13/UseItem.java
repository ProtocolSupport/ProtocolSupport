package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class UseItem extends MiddleBlockPlace {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		usedHand = VarNumberSerializer.readVarInt(clientdata);
		face = -1;
	}

}
