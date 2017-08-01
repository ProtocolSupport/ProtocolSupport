package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleResourcePackStatus;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ResourcePackStatus extends MiddleResourcePackStatus {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		StringSerializer.readString(clientdata, connection.getVersion(), 40);
		result = VarNumberSerializer.readVarInt(clientdata);
	}

}
