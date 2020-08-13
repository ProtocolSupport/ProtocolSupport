package protocolsupport.protocol.packet.middleimpl.serverbound.login.v_13_14r1_14r2_15_16r1_16r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.login.MiddleLoginCustomPayload;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class LoginCustomPayload extends MiddleLoginCustomPayload {

	public LoginCustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		id = VarNumberSerializer.readVarInt(clientdata);
		if (clientdata.readBoolean()) {
			data = MiscSerializer.readAllBytesSlice(clientdata, 1048576);
		} else {
			data = null;
		}
	}

}
