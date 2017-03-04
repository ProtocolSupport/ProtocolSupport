package protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10__1_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class UseItem extends MiddleBlockPlace {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		usedHand = VarNumberSerializer.readVarInt(clientdata);
		face = -1;
	}

}
