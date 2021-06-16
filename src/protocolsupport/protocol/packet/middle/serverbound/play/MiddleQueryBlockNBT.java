package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.Position;

public abstract class MiddleQueryBlockNBT extends ServerBoundMiddlePacket {

	protected MiddleQueryBlockNBT(MiddlePacketInit init) {
		super(init);
	}

	protected int id;
	protected final Position position = new Position(0, 0, 0);

	@Override
	protected void write() {
		ServerBoundPacketData queryblocknbt = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_QUERY_BLOCK_NBT);
		VarNumberSerializer.writeVarInt(queryblocknbt, id);
		PositionSerializer.writePosition(queryblocknbt, position);
		codec.writeServerbound(queryblocknbt);
	}

}
