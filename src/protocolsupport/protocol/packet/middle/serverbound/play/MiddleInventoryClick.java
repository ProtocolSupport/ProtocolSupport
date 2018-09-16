package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

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
		return RecyclableSingletonList.create(create(cache.getAttributesCache().getLocale(), windowId, slot, button, actionNumber, mode, itemstack));
	}

	public static ServerBoundPacketData create(String locale, int windowId, int slot, int button, int actionNumber, int mode, NetworkItemStack itemstack) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_WINDOW_CLICK);
		creator.writeByte(windowId);
		creator.writeShort(slot);
		creator.writeByte(button);
		creator.writeShort(actionNumber);
		creator.writeByte(mode);
		ItemStackSerializer.writeItemStack(creator, ProtocolVersionsHelper.LATEST_PC, locale, itemstack, false);
		return creator;
	}

}
