package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_l;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class LoginDisconnect extends MiddleLoginDisconnect {

	public LoginDisconnect(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LOGIN_DISCONNECT);
		StringSerializer.writeShortUTF16BEString(serializer, message.toLegacyText(cache.getClientCache().getLocale()));
		codec.write(serializer);
	}

}
