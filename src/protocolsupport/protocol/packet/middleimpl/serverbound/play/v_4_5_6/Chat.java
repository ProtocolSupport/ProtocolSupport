package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleChat;
import protocolsupport.protocol.serializer.StringSerializer;

public class Chat extends MiddleChat {

	public Chat(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void read(ByteBuf clientdata) {
		message = StringSerializer.readShortUTF16BEString(clientdata, Short.MAX_VALUE);
	}

}
