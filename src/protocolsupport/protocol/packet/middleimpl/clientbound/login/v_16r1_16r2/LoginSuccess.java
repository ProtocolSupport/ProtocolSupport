package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_16r1_16r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginSuccess;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;

public class LoginSuccess extends MiddleLoginSuccess {

	public LoginSuccess(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LOGIN_SUCCESS);
		UUIDSerializer.writeUUID4I(serializer, uuid);
		StringSerializer.writeVarIntUTF8String(serializer, name);
		codec.write(serializer);
	}

}
