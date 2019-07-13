package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleSpectate;
import protocolsupport.protocol.serializer.MiscSerializer;

public class Spectate extends MiddleSpectate {

	public Spectate(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		entityUUID = MiscSerializer.readUUID(clientdata);
	}

}
