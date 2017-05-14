package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;

//TODO: actually read fields
public abstract class MiddleAdvancements extends ClientBoundMiddlePacket {

	protected byte[] data;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		data = MiscSerializer.readAllBytes(serverdata);
	}

}
