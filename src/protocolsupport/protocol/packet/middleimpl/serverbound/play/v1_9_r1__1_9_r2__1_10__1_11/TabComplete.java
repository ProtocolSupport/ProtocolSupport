package protocolsupport.protocol.packet.middleimpl.serverbound.play.v1_9_r1__1_9_r2__1_10__1_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTabComplete;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class TabComplete extends MiddleTabComplete {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		string = StringSerializer.readString(clientdata, version);
		assumecommand = clientdata.readBoolean();
		if (clientdata.readBoolean()) {
			position = PositionSerializer.readPosition(clientdata);
		}
	}

}
