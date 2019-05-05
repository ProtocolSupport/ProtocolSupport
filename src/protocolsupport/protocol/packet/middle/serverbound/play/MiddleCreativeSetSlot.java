package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleCreativeSetSlot extends ServerBoundMiddlePacket {

	public MiddleCreativeSetSlot(ConnectionImpl connection) {
		super(connection);
	}

	protected int slot;
	protected NetworkItemStack itemstack;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(slot, itemstack));
	}

	public static ServerBoundPacketData create(int slot, NetworkItemStack itemstack) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CREATIVE_SET_SLOT);
		creator.writeShort(slot);
		ItemStackSerializer.writeItemStack(creator, itemstack);
		return creator;
	}

}
