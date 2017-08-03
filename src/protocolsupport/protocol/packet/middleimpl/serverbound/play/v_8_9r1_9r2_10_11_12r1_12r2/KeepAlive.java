package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleKeepAlive;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class KeepAlive extends MiddleKeepAlive {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		keepAliveId = VarNumberSerializer.readVarInt(clientdata);
	}

}
