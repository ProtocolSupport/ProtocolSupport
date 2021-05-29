package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;

public abstract class MiddleQueryNBTResponse extends ClientBoundMiddlePacket {

	protected MiddleQueryNBTResponse(MiddlePacketInit init) {
		super(init);
	}

	//TODO: structure
	protected ByteBuf data;

	@Override
	protected void decode(ByteBuf serverdata) {
		data = MiscSerializer.readAllBytesSlice(serverdata);
	}

}
