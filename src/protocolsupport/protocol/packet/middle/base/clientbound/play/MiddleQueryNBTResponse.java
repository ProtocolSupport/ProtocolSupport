package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleQueryNBTResponse extends ClientBoundMiddlePacket {

	protected MiddleQueryNBTResponse(IMiddlePacketInit init) {
		super(init);
	}

	//TODO: structure
	protected ByteBuf data;

	@Override
	protected void decode(ByteBuf serverdata) {
		data = MiscDataCodec.readAllBytesSlice(serverdata);
	}

}
