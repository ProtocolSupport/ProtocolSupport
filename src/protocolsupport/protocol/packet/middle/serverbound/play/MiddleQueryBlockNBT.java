package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.Position;

public abstract class MiddleQueryBlockNBT extends ServerBoundMiddlePacket {

	public MiddleQueryBlockNBT(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;
	protected final Position position = new Position(0, 0, 0);

	@Override
	protected void writeToServer() {
		ServerBoundPacketData queryblocknbt = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_QUERY_BLOCK_NBT);
		VarNumberSerializer.writeVarInt(queryblocknbt, id);
		PositionSerializer.writePosition(queryblocknbt, position);
		codec.read(queryblocknbt);
	}

}
