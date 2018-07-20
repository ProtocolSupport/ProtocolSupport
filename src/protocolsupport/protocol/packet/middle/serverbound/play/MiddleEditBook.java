package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public abstract class MiddleEditBook extends ServerBoundMiddlePacket {

	protected NetworkItemStack book;
	protected boolean signing;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_EDIT_BOOK);
		ItemStackSerializer.writeItemStack(creator, connection.getVersion(), cache.getAttributesCache().getLocale(), book, false);
		creator.writeBoolean(signing);
		return RecyclableSingletonList.create(creator);
	}

}
