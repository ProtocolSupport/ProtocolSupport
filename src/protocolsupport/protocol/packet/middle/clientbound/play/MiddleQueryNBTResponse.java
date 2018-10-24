package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;

public abstract class MiddleQueryNBTResponse extends ClientBoundMiddlePacket {

	public MiddleQueryNBTResponse(ConnectionImpl connection) {
		super(connection);
	}

	//TODO: structure
	protected ByteBuf data;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		data = MiscSerializer.readAllBytesSlice(serverdata);
	}

}
