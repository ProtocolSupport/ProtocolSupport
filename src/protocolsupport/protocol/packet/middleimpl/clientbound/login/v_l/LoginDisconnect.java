package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_l;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class LoginDisconnect extends MiddleLoginDisconnect {

	public LoginDisconnect(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacketType.LOGIN_DISCONNECT);
		StringSerializer.writeShortUTF16BEString(serializer, message.toLegacyText(cache.getClientCache().getLocale()));
		codec.writeClientbound(serializer);
	}

}
