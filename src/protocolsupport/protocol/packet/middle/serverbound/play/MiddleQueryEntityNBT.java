package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleQueryEntityNBT extends ServerBoundMiddlePacket {

	public MiddleQueryEntityNBT(ConnectionImpl connection) {
		super(connection);
	}

	protected int id;
	protected int entityId;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_QUERY_ENTITY_NBT);
		VarNumberSerializer.writeVarInt(creator, id);
		VarNumberSerializer.writeVarInt(creator, entityId);
		return RecyclableSingletonList.create(creator);
	}

}
