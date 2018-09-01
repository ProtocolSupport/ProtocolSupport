package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddlePickItem extends ServerBoundMiddlePacket {

	public MiddlePickItem(ConnectionImpl connection) {
		super(connection);
	}

	protected int slot;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(slot));
	}

	public static ServerBoundPacketData create(int slot) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacket.PLAY_PICK_ITEM);
		VarNumberSerializer.writeVarInt(serializer, slot);
		return serializer;
	}

}
