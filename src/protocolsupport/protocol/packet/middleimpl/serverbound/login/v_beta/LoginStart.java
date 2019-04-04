package protocolsupport.protocol.packet.middleimpl.serverbound.login.v_beta;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.login.MiddleLoginStart;
import protocolsupport.protocol.serializer.StringSerializer;

public class LoginStart extends MiddleLoginStart {

	public LoginStart(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		int versionId = clientdata.readInt();
		//TODO: switch version here
		name = StringSerializer.readString(clientdata, ProtocolVersion.getOldest(ProtocolType.PC));
		clientdata.skipBytes(Long.BYTES + Byte.BYTES);
	}

}
