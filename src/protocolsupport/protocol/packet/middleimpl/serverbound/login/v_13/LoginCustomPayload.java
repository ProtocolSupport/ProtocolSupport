package protocolsupport.protocol.packet.middleimpl.serverbound.login.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.login.MiddleLoginCustomPayload;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class LoginCustomPayload extends MiddleLoginCustomPayload {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		id = VarNumberSerializer.readVarInt(clientdata);
		if (clientdata.readBoolean()) {
			data = MiscSerializer.readAllBytesWithLimit(clientdata, 1048576);
		} else {
			data = null;
		}
	}

}
