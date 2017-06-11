package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_7_8_9r1_9r2_10_11_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.handshake.MiddleSetProtocol;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SetProtocol extends MiddleSetProtocol {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		VarNumberSerializer.readVarInt(clientdata);
		hostname = StringSerializer.readString(clientdata, version);
		port = clientdata.readUnsignedShort();
		nextState = VarNumberSerializer.readVarInt(clientdata);
	}

}
