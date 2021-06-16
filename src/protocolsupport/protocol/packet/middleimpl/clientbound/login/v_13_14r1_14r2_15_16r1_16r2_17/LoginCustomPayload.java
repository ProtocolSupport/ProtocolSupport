package protocolsupport.protocol.packet.middleimpl.clientbound.login.v_13_14r1_14r2_15_16r1_16r2_17;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.login.MiddleLoginCustomPayload;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class LoginCustomPayload extends MiddleLoginCustomPayload {

	public LoginCustomPayload(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData logincustompayload = ClientBoundPacketData.create(ClientBoundPacketType.LOGIN_CUSTOM_PAYLOAD);
		VarNumberSerializer.writeVarInt(logincustompayload, id);
		StringSerializer.writeVarIntUTF8String(logincustompayload, tag);
		logincustompayload.writeBytes(data);
		codec.writeClientbound(logincustompayload);
	}

}
