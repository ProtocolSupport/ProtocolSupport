package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.chat.ChatSerializer;

public class LoginDisconnect extends MiddleLoginDisconnect {

	public LoginDisconnect(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData logindisconnect = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LOGIN_DISCONNECT);
		StringSerializer.writeVarIntUTF8String(logindisconnect, ChatSerializer.serialize(version, cache.getClientCache().getLocale(), message));
		codec.write(logindisconnect);
	}

}
