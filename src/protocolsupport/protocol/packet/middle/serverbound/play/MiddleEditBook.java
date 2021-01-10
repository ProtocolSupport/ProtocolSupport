package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleEditBook extends ServerBoundMiddlePacket {

	public MiddleEditBook(MiddlePacketInit init) {
		super(init);
	}

	protected NetworkItemStack book;
	protected boolean signing;
	protected int slot;

	@Override
	protected void write() {
		codec.writeServerbound(create(book, signing, slot));
	}

	public static ServerBoundPacketData create(NetworkItemStack book, boolean signing, int slot) {
		ServerBoundPacketData editbook = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_EDIT_BOOK);
		ItemStackSerializer.writeItemStack(editbook, book);
		editbook.writeBoolean(signing);
		VarNumberSerializer.writeVarInt(editbook, slot);
		return editbook;
	}

}
