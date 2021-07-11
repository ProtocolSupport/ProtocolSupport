package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleEditBook extends ServerBoundMiddlePacket {

	protected MiddleEditBook(MiddlePacketInit init) {
		super(init);
	}

	protected int slot;
	protected String[] pages;
	protected String title;

	@Override
	protected void write() {
		codec.writeServerbound(create(slot, pages, title));
	}

	public static ServerBoundPacketData create(int slot, String[] pages, String title) {
		ServerBoundPacketData editbookPacket = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_EDIT_BOOK);
		VarNumberCodec.writeVarInt(editbookPacket, slot);
		ArrayCodec.writeVarIntVarIntUTF8StringArray(editbookPacket, pages);
		if (title != null) {
			editbookPacket.writeBoolean(true);
			StringCodec.writeVarIntUTF8String(editbookPacket, title);
		} else {
			editbookPacket.writeBoolean(false);
		}
		return editbookPacket;
	}

}
