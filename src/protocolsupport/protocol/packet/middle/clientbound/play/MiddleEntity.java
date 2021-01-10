package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleEntity extends ClientBoundMiddlePacket {

	public MiddleEntity(MiddlePacketInit init) {
		super(init);
	}

	protected int entityId;

	@Override
	protected void decode(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
	}

}
