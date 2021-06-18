package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.types.NetworkItemStack;

public abstract class MiddleEditBook extends ServerBoundMiddlePacket {

	protected MiddleEditBook(MiddlePacketInit init) {
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
		ServerBoundPacketData editbook = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_EDIT_BOOK);
		ItemStackCodec.writeItemStack(editbook, book);
		editbook.writeBoolean(signing);
		VarNumberCodec.writeVarInt(editbook, slot);
		return editbook;
	}

}
