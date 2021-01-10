package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_4_5_6;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class LoginDisconnect extends MiddleLoginDisconnect {

	public LoginDisconnect(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData logindisconnect = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LOGIN_DISCONNECT);
		StringSerializer.writeShortUTF16BEString(logindisconnect, message.toLegacyText(cache.getClientCache().getLocale()));
		codec.writeClientbound(logindisconnect);
	}

}
