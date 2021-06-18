package protocolsupport.protocol.packet.middle.clientbound.login;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleSetCompression extends ClientBoundMiddlePacket {

	protected MiddleSetCompression(MiddlePacketInit init) {
		super(init);
	}

	protected int threshold;

	@Override
	protected void decode(ByteBuf serverdata) {
		threshold = VarNumberCodec.readVarInt(serverdata);
	}

}
