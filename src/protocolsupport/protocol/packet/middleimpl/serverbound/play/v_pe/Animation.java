package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleAnimation;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class Animation extends MiddleAnimation {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		int action = VarNumberSerializer.readSVarInt(clientdata);
		VarNumberSerializer.readVarLong(clientdata);
		if ((action & 0x80) != 0) {
			clientdata.readFloatLE();
		}
	}

}
