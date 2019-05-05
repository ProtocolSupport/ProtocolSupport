package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleInventoryClick extends ServerBoundMiddlePacket {

	public MiddleInventoryClick(ConnectionImpl connection) {
		super(connection);
	}

	protected int windowId;
	protected int slot;
	protected int button;
	protected int actionNumber;
	protected int mode;
	protected NetworkItemStack itemstack;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(windowId, slot, button, actionNumber, mode, itemstack));
	}

	public static ServerBoundPacketData create(int windowId, int slot, int button, int actionNumber, int mode, NetworkItemStack itemstack) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_WINDOW_CLICK);
		creator.writeByte(windowId);
		creator.writeShort(slot);
		creator.writeByte(button);
		creator.writeShort(actionNumber);
		creator.writeByte(mode);
		ItemStackSerializer.writeItemStack(creator, itemstack);
		return creator;
	}

}
