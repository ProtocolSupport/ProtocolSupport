package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;
import protocolsupport.protocol.types.Position;

public abstract class MiddleQueryBlockNBT extends ServerBoundMiddlePacket {

	protected MiddleQueryBlockNBT(IMiddlePacketInit init) {
		super(init);
	}

	protected int id;
	protected final Position position = new Position(0, 0, 0);

	@Override
	protected void write() {
		ServerBoundPacketData queryblocknbt = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_QUERY_BLOCK_NBT);
		VarNumberCodec.writeVarInt(queryblocknbt, id);
		PositionCodec.writePosition(queryblocknbt, position);
		io.writeServerbound(queryblocknbt);
	}

}
