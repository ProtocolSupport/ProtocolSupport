package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_16r1_16r2;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginSuccess;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;

public class LoginSuccess extends MiddleLoginSuccess {

	public LoginSuccess(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacketType.LOGIN_SUCCESS);
		UUIDSerializer.writeUUID4I(serializer, uuid);
		StringSerializer.writeVarIntUTF8String(serializer, name);
		codec.writeClientbound(serializer);
	}

}
