package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_16r1_16r2_17r1_17r2;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginSuccess;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class LoginSuccess extends MiddleLoginSuccess {

	public LoginSuccess(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacketType.LOGIN_SUCCESS);
		UUIDCodec.writeUUID4I(serializer, uuid);
		StringCodec.writeVarIntUTF8String(serializer, name);
		codec.writeClientbound(serializer);
	}

}
