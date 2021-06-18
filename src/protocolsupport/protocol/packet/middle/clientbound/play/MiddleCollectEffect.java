package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleCollectEffect extends ClientBoundMiddlePacket {

	protected MiddleCollectEffect(MiddlePacketInit init) {
		super(init);
	}

	protected int entityId;
	protected int collectorId;
	protected int itemCount;

	@Override
	protected void decode(ByteBuf serverdata) {
		entityId = VarNumberCodec.readVarInt(serverdata);
		collectorId = VarNumberCodec.readVarInt(serverdata);
		itemCount = VarNumberCodec.readVarInt(serverdata);
	}

}
