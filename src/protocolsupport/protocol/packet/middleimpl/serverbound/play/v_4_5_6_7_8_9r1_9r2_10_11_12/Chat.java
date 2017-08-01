package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleChat;
import protocolsupport.protocol.serializer.StringSerializer;

public class Chat extends MiddleChat {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		message = StringSerializer.readString(clientdata, connection.getVersion());
	}

}
