package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_7_8_9r1_9r2_10_11_12;

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
