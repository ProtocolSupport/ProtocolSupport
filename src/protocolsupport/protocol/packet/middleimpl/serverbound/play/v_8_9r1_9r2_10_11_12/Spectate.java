package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSpecate;
import protocolsupport.protocol.serializer.MiscSerializer;

public class Spectate extends MiddleSpecate {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		entityUUID = MiscSerializer.readUUID(clientdata);
	}

}
