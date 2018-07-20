package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCustomPayload;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public class CustomPayload extends MiddleCustomPayload {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		tag = StringSerializer.readString(clientdata, connection.getVersion());
		data = MiscSerializer.readAllBytes(clientdata);
	}

}
