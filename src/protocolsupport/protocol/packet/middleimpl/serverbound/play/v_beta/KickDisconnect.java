package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public class KickDisconnect extends ServerBoundMiddlePacket {

	public KickDisconnect(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		StringSerializer.readShortUTF16BEString(clientdata, Short.MAX_VALUE);
	}

	@Override
	public void writeToServer() {
	}

}
