package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public class KickDisconnect extends ServerBoundMiddlePacket {

	public KickDisconnect(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		StringSerializer.readShortUTF16BEString(clientdata, 32);
	}

	@Override
	protected void writeToServer() {
	}

}
