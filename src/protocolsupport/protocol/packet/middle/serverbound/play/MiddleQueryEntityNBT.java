package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
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
	public RecyclableCollection<? extends IPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_QUERY_ENTITY_NBT);
		VarNumberSerializer.writeVarInt(creator, id);
		VarNumberSerializer.writeVarInt(creator, entityId);
		return RecyclableSingletonList.create(creator);
	}

}
