package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.UsedHand;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleEditBook extends ServerBoundMiddlePacket {

	public MiddleEditBook(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkItemStack book;
	protected boolean signing;
	protected UsedHand hand;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(book, signing, hand));
	}

	public static ServerBoundPacketData create(NetworkItemStack book, boolean signing, UsedHand hand) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_EDIT_BOOK);
		ItemStackSerializer.writeItemStack(creator, book);
		creator.writeBoolean(signing);
		MiscSerializer.writeVarIntEnum(creator, hand);
		return creator;
	}

}
