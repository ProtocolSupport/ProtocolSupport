package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_7__1_8__1_9_r1__1_9_r2__1_10__1_11;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientCommand;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ClientCommand extends MiddleClientCommand {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		command = VarNumberSerializer.readVarInt(clientdata);
	}

}
