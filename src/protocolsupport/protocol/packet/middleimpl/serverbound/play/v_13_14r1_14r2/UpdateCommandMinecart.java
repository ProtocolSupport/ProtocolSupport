package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleUpdateCommandMinecart;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class UpdateCommandMinecart extends MiddleUpdateCommandMinecart {

	public UpdateCommandMinecart(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		entityId = VarNumberSerializer.readVarInt(clientdata);
		command = StringSerializer.readString(clientdata, version);
		trackOutput = clientdata.readBoolean();
	}

}
