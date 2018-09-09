package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.types.UsedHand;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.NetworkItemStack;

public abstract class MiddleEditBook extends ServerBoundMiddlePacket {

	public MiddleEditBook(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkItemStack book;
	protected boolean signing;
	protected UsedHand hand;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(cache.getAttributesCache().getLocale(), book, signing, hand));
	}

	public static ServerBoundPacketData create(String locale, NetworkItemStack book, boolean signing, UsedHand hand) {
		ServerBoundPacketData serializer = ServerBoundPacketData.create(ServerBoundPacket.PLAY_EDIT_BOOK);
		ItemStackSerializer.writeItemStack(serializer, ProtocolVersionsHelper.LATEST_PC, locale, book, false);
		serializer.writeBoolean(signing);
		MiscSerializer.writeVarIntEnum(serializer, hand);
		return serializer;
	}

}
