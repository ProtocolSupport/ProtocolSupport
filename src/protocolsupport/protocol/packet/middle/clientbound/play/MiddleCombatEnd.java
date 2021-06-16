package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleCombatEnd extends ClientBoundMiddlePacket {

	protected MiddleCombatEnd(MiddlePacketInit init) {
		super(init);
	}

	protected int duration;
	protected int opponentId;

	@Override
	protected void decode(ByteBuf serverdata) {
		this.duration = VarNumberSerializer.readVarInt(serverdata);
		this.opponentId = serverdata.readInt();
	}

}
