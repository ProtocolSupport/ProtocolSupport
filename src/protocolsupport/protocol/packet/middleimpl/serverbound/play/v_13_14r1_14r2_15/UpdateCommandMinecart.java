package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2_15;

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
	protected void readClientData(ByteBuf clientdata) {
		entityId = VarNumberSerializer.readVarInt(clientdata);
		command = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
		trackOutput = clientdata.readBoolean();
	}

}
