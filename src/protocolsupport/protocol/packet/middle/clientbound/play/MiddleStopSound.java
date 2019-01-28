package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleStopSound extends ClientBoundMiddlePacket {

	protected static final int FLAG_SOURCE = 0x1;
	protected static final int FLAG_NAME = 0x2;

	public MiddleStopSound(ConnectionImpl connection) {
		super(connection);
	}

	protected int source = -1;
	protected String name;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		int flags = serverdata.readByte();
		source = (flags & FLAG_SOURCE) == FLAG_SOURCE ? VarNumberSerializer.readVarInt(serverdata) : -1;
		name = (flags & FLAG_NAME) == FLAG_NAME ? StringSerializer.readVarIntUTF8String(serverdata) : null;
	}

}
