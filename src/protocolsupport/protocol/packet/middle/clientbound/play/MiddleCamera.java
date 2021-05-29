package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleCamera extends ClientBoundMiddlePacket {

	protected MiddleCamera(MiddlePacketInit init) {
		super(init);
	}

	protected int entityId;

	@Override
	protected void decode(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
	}

}
