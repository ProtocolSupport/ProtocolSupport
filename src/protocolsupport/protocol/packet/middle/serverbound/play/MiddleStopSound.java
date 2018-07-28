package protocolsupport.protocol.packet.middle.serverbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;

public abstract class MiddleStopSound extends ClientBoundMiddlePacket {

	//TODO: structure
	protected byte[] data;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		data = MiscSerializer.readAllBytes(serverdata);
	}

}
