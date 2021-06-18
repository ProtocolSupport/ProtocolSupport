package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_4_5_6;

import protocolsupport.protocol.codec.StringCodec;
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
		StringCodec.writeShortUTF16BEString(logindisconnect, message.toLegacyText(cache.getClientCache().getLocale()));
		codec.writeClientbound(logindisconnect);
	}

}
