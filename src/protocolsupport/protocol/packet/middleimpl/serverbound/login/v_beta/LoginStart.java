package protocolsupport.protocol.packet.middleimpl.serverbound.login.v_beta;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.login.MiddleLoginStart;
import protocolsupport.protocol.serializer.StringSerializer;

public class LoginStart extends MiddleLoginStart {

	public LoginStart(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		clientdata.readInt(); //version id
		//TODO: switch version here somehow
		name = StringSerializer.readShortUTF16BEString(clientdata, Short.MAX_VALUE);
		clientdata.skipBytes(Long.BYTES + Byte.BYTES);
	}

}
