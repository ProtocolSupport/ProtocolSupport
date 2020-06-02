package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14r1_14r2_15;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleNameItem;
import protocolsupport.protocol.serializer.StringSerializer;

public class NameItem extends MiddleNameItem {

	public NameItem(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readClientData(ByteBuf clientdata) {
		name = StringSerializer.readVarIntUTF8String(clientdata, Short.MAX_VALUE);
	}

}
