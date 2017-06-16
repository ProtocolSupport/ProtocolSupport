package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleTabComplete;
import protocolsupport.protocol.serializer.StringSerializer;

public class TabComplete extends MiddleTabComplete {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		string = StringSerializer.readString(clientdata, version);
	}

}
