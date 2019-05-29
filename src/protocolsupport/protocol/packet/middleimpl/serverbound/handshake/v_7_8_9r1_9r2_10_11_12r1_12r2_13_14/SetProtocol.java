package protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.handshake.MiddleSetProtocol;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class SetProtocol extends MiddleSetProtocol {

	public SetProtocol(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		VarNumberSerializer.readVarInt(clientdata);
		hostname = StringSerializer.readString(clientdata, version);
		port = clientdata.readUnsignedShort();
		nextState = VarNumberSerializer.readVarInt(clientdata);
	}

}
