package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleCollectEffect extends ClientBoundMiddlePacket {

	public MiddleCollectEffect(MiddlePacketInit init) {
		super(init);
	}

	protected int entityId;
	protected int collectorId;
	protected int itemCount;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		collectorId = VarNumberSerializer.readVarInt(serverdata);
		itemCount = VarNumberSerializer.readVarInt(serverdata);
	}

}
