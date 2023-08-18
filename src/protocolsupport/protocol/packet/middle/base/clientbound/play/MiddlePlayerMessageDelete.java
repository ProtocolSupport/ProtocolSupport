package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddlePlayerMessageDelete extends ClientBoundMiddlePacket {

	protected MiddlePlayerMessageDelete(IMiddlePacketInit init) {
		super(init);
	}

	protected byte[] signature;

	@Override
	protected void decode(ByteBuf serverdata) {
		signature = ArrayCodec.readVarIntByteArray(serverdata);
	}

}
