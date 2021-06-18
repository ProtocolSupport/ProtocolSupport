package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class LoginDisconnect extends MiddleLoginDisconnect {

	public LoginDisconnect(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData logindisconnect = ClientBoundPacketData.create(ClientBoundPacketType.LOGIN_DISCONNECT);
		StringCodec.writeVarIntUTF8String(logindisconnect, ChatCodec.serialize(version, cache.getClientCache().getLocale(), message));
		codec.writeClientbound(logindisconnect);
	}

}
