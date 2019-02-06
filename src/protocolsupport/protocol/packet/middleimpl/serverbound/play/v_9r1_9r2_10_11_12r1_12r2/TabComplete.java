package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12r1_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTabComplete;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class TabComplete extends MiddleTabComplete {

	public TabComplete(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		id = 0;
		string = StringSerializer.readString(clientdata, version, 256);
		if (string.startsWith("/")) {
			string = string.substring(1);
		}
		clientdata.readBoolean();
		if (clientdata.readBoolean()) {
			PositionSerializer.skipPosition(clientdata);
		}
	}

}
