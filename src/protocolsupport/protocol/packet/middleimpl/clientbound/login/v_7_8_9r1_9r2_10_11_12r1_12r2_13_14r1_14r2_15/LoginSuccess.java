package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginSuccess;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class LoginSuccess extends MiddleLoginSuccess {

	public LoginSuccess(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		String uuidstring = uuid.toString();
		if (version == ProtocolVersion.MINECRAFT_1_7_5) {
			uuidstring = uuidstring.replace("-", "");
		}
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LOGIN_SUCCESS);
		StringSerializer.writeVarIntUTF8String(serializer, uuidstring);
		StringSerializer.writeVarIntUTF8String(serializer, name);
		codec.write(serializer);
	}

}
