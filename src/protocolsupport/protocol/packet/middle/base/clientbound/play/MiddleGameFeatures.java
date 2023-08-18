package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleGameFeatures extends ClientBoundMiddlePacket {

	protected MiddleGameFeatures(IMiddlePacketInit init) {
		super(init);
	}

	protected String[] flags;

	@Override
	protected void decode(ByteBuf serverdata) {
		flags = ArrayCodec.readVarIntVarIntUTF8StringArray(serverdata);
	}

}
