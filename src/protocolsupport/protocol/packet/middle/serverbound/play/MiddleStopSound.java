package protocolsupport.protocol.packet.middle.serverbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;

public abstract class MiddleStopSound extends ClientBoundMiddlePacket {

	public MiddleStopSound(ConnectionImpl connection) {
		super(connection);
	}

	//TODO: structure
	protected ByteBuf data;
	protected String name = "";
	protected boolean stopAll = true;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		data = MiscSerializer.readAllBytesSlice(serverdata);
	}

}
