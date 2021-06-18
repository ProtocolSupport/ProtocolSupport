package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_l;

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
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacketType.LOGIN_DISCONNECT);
		StringCodec.writeShortUTF16BEString(serializer, message.toLegacyText(cache.getClientCache().getLocale()));
		codec.writeClientbound(serializer);
	}

}
