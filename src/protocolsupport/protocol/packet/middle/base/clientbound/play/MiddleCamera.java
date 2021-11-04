package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleCamera extends ClientBoundMiddlePacket {

	protected MiddleCamera(IMiddlePacketInit init) {
		super(init);
	}

	protected int entityId;

	@Override
	protected void decode(ByteBuf serverdata) {
		entityId = VarNumberCodec.readVarInt(serverdata);
	}

}
