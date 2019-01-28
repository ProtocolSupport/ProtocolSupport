package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleScoreboardDisplay extends ClientBoundMiddlePacket {

	public MiddleScoreboardDisplay(ConnectionImpl connection) {
		super(connection);
	}

	protected int position;
	protected String name;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		position = serverdata.readUnsignedByte();
		name = StringSerializer.readVarIntUTF8String(serverdata);
	}

}
