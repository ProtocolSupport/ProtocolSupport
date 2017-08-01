package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_10_11_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleResourcePackStatus;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ResourcePackStatus extends MiddleResourcePackStatus {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		result = VarNumberSerializer.readVarInt(clientdata);
	}

}
