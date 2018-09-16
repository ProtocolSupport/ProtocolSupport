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

public abstract class MiddleCreativeSetSlot extends ServerBoundMiddlePacket {

	public MiddleCreativeSetSlot(ConnectionImpl connection) {
		super(connection);
	}

	protected int slot;
	protected NetworkItemStack itemstack;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(cache.getAttributesCache().getLocale(), slot, itemstack));
	}

	public static ServerBoundPacketData create(String locale, int slot, NetworkItemStack itemstack) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CREATIVE_SET_SLOT);
		creator.writeShort(slot);
		ItemStackSerializer.writeItemStack(creator, ProtocolVersionsHelper.LATEST_PC, locale, itemstack, false);
		return creator;
	}

}
