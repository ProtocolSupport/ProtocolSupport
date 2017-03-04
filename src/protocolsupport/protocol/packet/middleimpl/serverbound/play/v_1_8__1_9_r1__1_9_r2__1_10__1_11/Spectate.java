package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_1_8__1_9_r1__1_9_r2__1_10__1_11;

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
