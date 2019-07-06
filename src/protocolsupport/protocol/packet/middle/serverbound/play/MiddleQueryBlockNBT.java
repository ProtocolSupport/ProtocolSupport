package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.Position;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleQueryBlockNBT extends ServerBoundMiddlePacket {

	public MiddleQueryBlockNBT(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;
	protected final Position position = new Position(0, 0, 0);

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_QUERY_BLOCK_NBT);
		VarNumberSerializer.writeVarInt(creator, id);
		PositionSerializer.writePosition(creator, position);
		return RecyclableSingletonList.create(creator);
	}

}
