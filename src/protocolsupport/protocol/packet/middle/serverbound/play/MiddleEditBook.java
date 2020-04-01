package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.UsedHand;

public abstract class MiddleEditBook extends ServerBoundMiddlePacket {

	public MiddleEditBook(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkItemStack book;
	protected boolean signing;
	protected UsedHand hand;

	@Override
	public void writeToServer() {
		codec.read(create(book, signing, hand));
	}

	public static ServerBoundPacketData create(NetworkItemStack book, boolean signing, UsedHand hand) {
		ServerBoundPacketData editbook = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_EDIT_BOOK);
		ItemStackSerializer.writeItemStack(editbook, book);
		editbook.writeBoolean(signing);
		MiscSerializer.writeVarIntEnum(editbook, hand);
		return editbook;
	}

}
