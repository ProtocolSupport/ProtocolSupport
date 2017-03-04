package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_10__1_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleResourcePackStatus;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ResourcePackStatus extends MiddleResourcePackStatus {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		result = VarNumberSerializer.readVarInt(clientdata);
	}

}
