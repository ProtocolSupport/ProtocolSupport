package protocolsupport.protocol.packet.middle.impl.clientbound.login.v_20;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.ProfileCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.login.MiddleLoginSuccess;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;

public class LoginSuccess extends MiddleLoginSuccess implements
IClientboundMiddlePacketV20 {

	public LoginSuccess(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacketType.LOGIN_SUCCESS);
		UUIDCodec.writeUUID(serializer, uuid);
		StringCodec.writeVarIntUTF8String(serializer, name);
		ArrayCodec.writeVarIntTArray(serializer, properties, ProfileCodec::writeProfileProperty);
		io.writeClientbound(serializer);
	}

}
