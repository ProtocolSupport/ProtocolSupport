package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class LoginCustomPayload extends MiddleLoginCustomPayload {

	public LoginCustomPayload(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData logincustompayload = ClientBoundPacketData.create(PacketType.CLIENTBOUND_LOGIN_CUSTOM_PAYLOAD);
		VarNumberSerializer.writeVarInt(logincustompayload, id);
		StringSerializer.writeVarIntUTF8String(logincustompayload, tag);
		logincustompayload.writeBytes(data);
		codec.write(logincustompayload);
	}

}
