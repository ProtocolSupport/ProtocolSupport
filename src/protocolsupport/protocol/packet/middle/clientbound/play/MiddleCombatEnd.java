package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleCombatEnd extends ClientBoundMiddlePacket {

	protected MiddleCombatEnd(MiddlePacketInit init) {
		super(init);
	}

	protected int duration;
	protected int opponentId;

	@Override
	protected void decode(ByteBuf serverdata) {
		this.duration = VarNumberCodec.readVarInt(serverdata);
		this.opponentId = serverdata.readInt();
	}

}
